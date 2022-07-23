package game;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;
import engine.GameItem;
import engine.IGameLogic;
import engine.MouseInput;
import engine.Window;
import engine.graph.Camera;
import engine.graph.DirectionalLight;
import engine.graph.Material;
import engine.graph.Mesh;
import engine.graph.OBJLoader;
import engine.graph.PointLight;
import engine.graph.SpotLight;
import engine.graph.Texture;

//essa classe carrega os objetos e a camera e atualiza o que a camera esta vendo
//essa classe so atualiza as entradas do teclado e do mause
public class DummyGame implements IGameLogic {

	private static final float MOUSE_SENSITIVITY = 0.2f;

	private final Vector3f cameraInc;

	private final Renderer renderer;

	private final Camera camera;

	private GameItem[] gameItems;

	private Vector3f ambientLight;

	private PointLight[] pointLightList;

	private SpotLight[] spotLightList;

	private DirectionalLight directionalLight;

	private float lightAngle;

	private static final float CAMERA_POS_STEP = 1f;

	private float spotAngle = 0;

	private float spotInc = 1;
	
	private String nome;

	public DummyGame() {
		renderer = new Renderer();
		camera = new Camera();
		cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
		lightAngle = -90;
	}
	
	public DummyGame(String nome) {
		renderer = new Renderer();
		camera = new Camera();
		cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
		lightAngle = -90;
		this.nome=nome;
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);
		gameItems = new GameItem[10];
		float reflectance = 5f;

		Texture textures[] = {
				new Texture("textures/sol.jpg"),
				new Texture("textures/mercurio.jpg"),
				new Texture("textures/venus.jpg"),
				new Texture("textures/terra.jpg"),
				new Texture("textures/marte.jpg"),
				new Texture("textures/jupiter.jpg"),
				new Texture("textures/saturno.jpg"),
				new Texture("textures/urano.jpg"),
				new Texture("textures/netuno.jpg"),
				new Texture("textures/lua.jpg")
		};
		
		float escalas[] = {
				15f,0.5f,1f,1f,0.6f,5f,3f,2f,2f,0.5f
		};

		for (int i = 0; i < gameItems.length; i++) {
			
		Mesh mesh = OBJLoader.loadMesh("/models/"+nome+".obj");
		Texture texture = textures[i];
		Material material = new Material(texture, reflectance);

		mesh.setMaterial(material);
		GameItem gameItem = new GameItem(mesh);
		gameItem.setPosition(0, 0, -20*i);
		gameItem.setScale(escalas[i]);
		
		gameItems[i] =  gameItem ;
		}
		
		//faz o sol nao ser dividido por 0
		gameItems[0].setPosition(0f, 0f, 0.000001f);
		
		//ajusta a lua
		gameItems[9].setPosition(gameItems[3].getPosition().x + 2f,
				gameItems[3].getPosition().y, gameItems[3].getPosition().z + 2f);
		
		gameItems[9].setTarget(gameItems[3].getPosition());


		//gameItems[4].move();
		ambientLight = new Vector3f(0.2f, 0.2f, 0.2f);

		// Point Light
		//posicao do sol
		Vector3f lightPosition = gameItems[0].getPosition();
		float lightIntensity = 1.0f;
		PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
		PointLight.Attenuation att = new PointLight.Attenuation(0.0001f, 0.0001f, 0.0001f);
		pointLight.setAttenuation(att);
		
		PointLight pointLight2 = new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, 14, 0), lightIntensity);
		PointLight pointLight3 = new PointLight(new Vector3f(1, 1, 1), new Vector3f(0, -14, 0), lightIntensity);
		
		pointLight2.setAttenuation(att);
		pointLight3.setAttenuation(att);
		
		pointLightList = new PointLight[] {pointLight,pointLight2,pointLight3};

		// Spot Light
		
		//lightPosition = new Vector3f(0, 30f, 0f);
		//pointLight = new PointLight(new Vector3f(0, 0, 0), lightPosition, lightIntensity);
		//att = new PointLight.Attenuation(0.001f, 0.001f, 0.001f);
		//pointLight.setAttenuation(att);
		//Vector3f coneDir = new Vector3f(0, -1f, 0);
		//float cutoff = (float) Math.cos(Math.toRadians(0));
		//SpotLight spotLight = new SpotLight(pointLight, coneDir, cutoff);
		//spotLightList = new SpotLight[0]; //{ spotLight, new SpotLight(spotLight) };

		//lightPosition = new Vector3f(1, 0, 0);
		//directionalLight = new DirectionalLight(new Vector3f(0, 0, 0), lightPosition, lightIntensity);
	}

	@Override
	public void input(Window window, MouseInput mouseInput) {
		cameraInc.set(0, 0, 0);
		if (window.isKeyPressed(GLFW_KEY_W)) {
			cameraInc.z = -1;
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			cameraInc.z = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_A)) {
			cameraInc.x = -1;
		} else if (window.isKeyPressed(GLFW_KEY_D)) {
			cameraInc.x = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_Z)) {
			cameraInc.y = -1;
		} else if (window.isKeyPressed(GLFW_KEY_X)) {
			cameraInc.y = 1;
		}
		//float lightPos = spotLightList[0].getPointLight().getPosition().z;
		//if (window.isKeyPressed(GLFW_KEY_N)) {
		//	this.spotLightList[0].getPointLight().getPosition().z = lightPos + 0.1f;
		//} else if (window.isKeyPressed(GLFW_KEY_M)) {
		//	this.spotLightList[0].getPointLight().getPosition().z = lightPos - 0.1f;
		//}
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		// Update camera position
		camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP,
				cameraInc.z * CAMERA_POS_STEP);

		// Update camera based on mouse
		if (mouseInput.isRightButtonPressed()) {
			camera.setHaveTarget(false);
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
		}
		
		// atualiza camera fazendo ela ir para cima e para baixo
		if(mouseInput.isLeftButtonPressed()) {
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.setTarget(0f, 0f, 0f);
			camera.setHaveTarget(true);
			camera.movePosition(rotVec.x * CAMERA_POS_STEP, rotVec.y * CAMERA_POS_STEP,0);
		}

		// Update spot light direction
		//spotAngle =0;
		//if (spotAngle > 2) {
		//	spotInc = -1;
		//} else if (spotAngle < -2) {
		//	spotInc = 1;
		//}
		//double spotAngleRad = Math.toRadians(spotAngle);
		//Vector3f coneDir = spotLightList[0].getConeDirection();
		//coneDir.y = (float) Math.sin(spotAngleRad);

		
	}

	@Override
	public void render(Window window) {
		renderer.render(window, camera, gameItems, ambientLight, pointLightList, spotLightList, directionalLight);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		for (GameItem gameItem : gameItems) {
			gameItem.getMesh().cleanUp();
		}
	}

}
