package engine;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import engine.collision.CollisionListener;
import engine.collision.CollisionManager;
import engine.drawing.BufferedDrawingCanvas;
import engine.events.ArrowKeyEvent;
import engine.events.EngineArrowKeyListener;
import engine.events.EngineKeyEvent;
import engine.events.EngineKeyListener;
import engine.events.EngineMouseEvent;
import engine.events.EngineMouseListener;
import engine.events.InputEvent;
import engine.util.EntitySort;
import engine.util.EntitySort.SortOptions;

public class Game {
	public int entities;

	private ArrayList<ArrayList<Entity>> _layers;

	private ArrayList<EngineKeyListener> _keyListeners;
	private ArrayList<EngineMouseListener> _mouseListeners;
	private ArrayList<EngineArrowKeyListener> _arrowListener;

	// keeps track of where in the struct are entities for quick removal
	private HashMap<Entity, Integer> _entityLocation;

	private Thread _gameThread, _inputThread;

	private volatile boolean _running, _paused;

	private int _fpsSleep;

	private InputManager _inputManager;

	private CollisionManager _collisionManager;

	private BufferedDrawingCanvas _dc;

	public static boolean DEBUGGING;

	public Game(int displayWidth, int displayHeight, int fps, boolean debug) {
		// set up the drawing canvas
		JFrame jf = new JFrame();
		
		DEBUGGING = debug;
		
		
		_dc = new BufferedDrawingCanvas(displayWidth, displayHeight);
		
		jf.add(_dc);

		jf.setVisible(true);
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jf.pack();
		
		// init the collision manager
		_collisionManager = new CollisionManager();

		// add the input manager
		_inputManager = new InputManager();
		jf.addKeyListener(_inputManager);
		jf.addMouseListener(_inputManager);

		// init the data structures

		// holds all of the layers
		_layers = new ArrayList<ArrayList<Entity>>();

		// start off with 1 layer (0)
		_layers.add(new ArrayList<Entity>());

		// stores where entities are (which layer
		_entityLocation = new HashMap<>();

		_keyListeners = new ArrayList<>();
		_mouseListeners = new ArrayList<>();
		_arrowListener = new ArrayList<>();

		_fpsSleep = 1000 / fps;

		_running = true;
		_paused = false;
		// make the game Thread

		if (DEBUGGING) {
			debug();
		}

		_gameThread = new Thread(new GameThread());
		_gameThread.start();

	}

	public Game(int displayWidth, int displayHeight, int fps) {
		this(displayWidth, displayHeight, fps, false);
	}

	public void addEntity(Entity e) {
		if(_entityLocation.containsKey(e)){
			return; //do not add multiple times
		}
		
		entities++;

		// layer 0
		_entityLocation.put(e, 0);
		_layers.get(0).add(e);

	}

	/**
	 * Places an entity in the nth layer. If the layer does not exist it will
	 * add it.
	 * 
	 * @param e
	 * @param layer
	 */
	public void addEntity(Entity e, Integer layer) {
		if(_entityLocation.containsKey(e)){
			return; //do not add multiple times
		}
		
		entities++;

		_entityLocation.put(e, layer);

		if (layer >= _layers.size()) {
			for (int i = _layers.size(); i < layer + 1; i++) {
				_layers.add(new ArrayList<Entity>());
			}

		}

		_layers.get(layer).add(e);
	}

	public void addCollisionObject(CollisionListener cl) {
		_collisionManager.addCollisionObject(cl);
	}

	public void removeEntity(Entity e) {
		entities--;

		_layers.get(_entityLocation.get(e)).remove(e);
	}

	public void removeCollisionObject(CollisionListener cl) {
		_collisionManager.removeCollisionObject(cl);
	}

	private class GameThread implements Runnable {

		// v THIS IS DEBUGGING INFORMATION v

		long total_time = 0;
		long total_time_taken = 0;

		long input_time = 0;
		long input_time_taken = 0;

		long update_time = 0;
		long update_time_taken = 0;

		long collision_time = 0;
		long collision_time_total = 0;

		long draw_time = 0;
		long draw_time_total = 0;

		// ^ THIS IS DEBUGGING INFORMATION ^

		@Override
		public void run() {

			// TODO Auto-generated method stub
			while (_running) {
				// DEBUGGING TOTAL TIME:
				if (!_paused) {
					total_time = System.currentTimeMillis();

					// DEBUGGING INPUT TIME:

					input_time = System.currentTimeMillis();

					final ArrayList<InputEvent> _ie = _inputManager.poll2();

					// dispatch events while doing other stuff
					_inputThread = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							for (int i2 = 0; i2 < _ie.size(); i2++) {
								InputEvent ie = _ie.get(i2);

								if (ie != null) {
									switch (ie.getType()) {
									case ActionEvent:
										// unimplemented
										break;
									case ArrowKeyEvent:
										for (int i = 0; i < _arrowListener
												.size(); i++) {
											_arrowListener.get(i).inputEvent(
													(ArrowKeyEvent) ie);
										}

										break;
									case KeyEvent:
										for (int i = 0; i < _keyListeners
												.size(); i++) {
											_keyListeners.get(i).inputEvent(
													(EngineKeyEvent) ie);
										}
										break;
									case MouseEvent:
										EngineMouseEvent eme = (EngineMouseEvent) ie;

										for (int i = 0; i < _mouseListeners
												.size(); i++) {
											synchronized (_layers) {
												if (_mouseListeners
														.get(i)
														.getBoundingBox()
														.contains(eme.getX(),
																eme.getY())) {
													_mouseListeners.get(i)
															.onClick(eme);
												}
											}

										}

										break;
									default:
										break;

									}
								}
							}
						}
					});

					_inputThread.start();

					// DEBUGGING INPUT TIME:
					input_time_taken = System.currentTimeMillis() - input_time;

					// DEBUGGING UPDATE TIME:
					update_time = System.currentTimeMillis();

					for (int i = 0; i < _layers.size(); i++) {
						for (int j = 0; j < _layers.get(i).size(); j++) {
							_layers.get(i).get(j).update();
						}
					}

					// DEBUGGING UPDATE TIME:
					update_time_taken = System.currentTimeMillis()
							- update_time;

					// DEBUGGING COLLISION TIME:
					collision_time = System.currentTimeMillis();

					// check collisions
					_collisionManager.checkCollisions();

					// DEBUGGING COLLISION TIME:
					collision_time_total = System.currentTimeMillis()
							- collision_time;

					// DEBUGGING DRAW TIME:
					draw_time = System.currentTimeMillis();
				}

				_dc.fill(Color.BLACK);
				
				do {
					for (int i = 0; i < _layers.size(); i++) {			
						//sort the entities so that they draw properly
						//that is so if an etity has a higher y, it will be draw first
						EntitySort.sortEntities(_layers.get(i), SortOptions.Y_AXIS);
						for (int j = 0; j < _layers.get(i).size(); j++) {
							_layers.get(i).get(j).draw(_dc);
						}
					}
				} while (_dc.contentsLost());
				
				_dc.flip();
				
				_dc.repaint();
				
				Toolkit.getDefaultToolkit().sync();
				
				// DEBUGGING DRAW TIME:
				draw_time_total = System.currentTimeMillis() - draw_time;

				// DEBUGGING TOTAL TIME
				total_time_taken = System.currentTimeMillis() - total_time;

				// display debug info
				if (DEBUGGING) {

					updateDebug(total_time_taken, input_time_taken,
							update_time_taken, collision_time_total,
							draw_time_total, entities);
				}
				// implement sleep timer
				try {
					if (_fpsSleep - total_time > 0) {
						Thread.sleep(_fpsSleep - total_time_taken);
					} else {
						Thread.sleep(_fpsSleep);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.err.println("Bye!");
					break;
				}
			}
		}

	}

	public void addEngineMouseListener(EngineMouseListener eml) {
		_mouseListeners.add(eml);
	}

	public void removeEngineMouseListener(EngineMouseListener eml) {
		_mouseListeners.remove(eml);
	}

	public void addEngineKeyListener(EngineKeyListener b) {
		// TODO Auto-generated method stub
		_keyListeners.add(b);
	}

	public void removeEngineKeyListener(EngineKeyListener b) {
		_keyListeners.remove(b);
	}

	public void addEngineArrowKeyListener(EngineArrowKeyListener b) {
		_arrowListener.add(b);
	}

	public void removeEngineArrowKeyListener(EngineArrowKeyListener b) {
		_arrowListener.remove(b);
	}

	/*
	 * DEBUGGING
	 */

	private JFrame jf;
	private JLabel gameTime;
	private JLabel inputTime;
	private JLabel updateTime;
	private JLabel collisionTime;
	private JLabel drawTime;
	private JLabel totalEntities;

	private void debug() {
		// TODO Auto-generated method stub
		jf = new JFrame("DEBUGGING");
		jf.setLayout(new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS));

		gameTime = new JLabel();
		inputTime = new JLabel();
		updateTime = new JLabel();
		collisionTime = new JLabel();
		drawTime = new JLabel();
		totalEntities = new JLabel();

		jf.add(gameTime);
		jf.add(inputTime);
		jf.add(updateTime);
		jf.add(collisionTime);
		jf.add(drawTime);
		jf.add(totalEntities);

		jf.pack();
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private void updateDebug(long gameTime, long inputTime, long updateTime,
			long collisionTime, long drawTime, int totalEntities) {
		this.gameTime.setText("GAME TIME: " + gameTime + " ms");
		this.inputTime.setText("INPUT TIME: " + inputTime + " ms (BAD STAT)");
		this.updateTime.setText("UPDATE TIME: " + updateTime + " ms");
		this.collisionTime.setText("COLLISION TIME: " + collisionTime + " ms");
		this.drawTime.setText("DRAW TIME: " + drawTime + " ms");
		this.totalEntities.setText("TOTAL ENTITIES " + totalEntities);

		jf.pack();
	}

	public void quit() {
		_running = false;
		// while (true) {
		_gameThread.interrupt();
		// }
	}

	public void pause() {
		_paused = true;
	}
}
