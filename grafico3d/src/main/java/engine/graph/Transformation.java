package engine.graph;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.GameItem;

// essa classe calcula qual vertice estamos lendo na camera
public class Transformation {

	private final Matrix4f projectionMatrix;

	private final Matrix4f modelViewMatrix;

	private final Matrix4f viewMatrix;
	

	public Transformation() {
		projectionMatrix = new Matrix4f();
		modelViewMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
	}

	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
		return projectionMatrix.setPerspective(fov, width / height, zNear, zFar);
	}

	public Matrix4f getViewMatrix(Camera camera) {
		Vector3f cameraPos = camera.getPosition();
		Vector3f rotation = camera.getRotation();
		Vector3f target = camera.getTarget();

		viewMatrix.identity();

		//define a direcao e posicao da camera
		if(camera.haveTarget()) {
			// tentativa de fazer a camera forcar no target
			viewMatrix.lookAt(cameraPos, target, new Vector3f(0f,1f,0f));
			viewMatrix.getEulerAnglesXYZ(rotation);
			camera.setRotation((float)(rotation.x*Math.PI*2),(float)( rotation.y*Math.PI*2),
					(float)(rotation.z*Math.PI*2));
			
		}else {
			// visao normal
			// First do the rotation so camera rotates over its position
			viewMatrix.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
						.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
			// Then do the translation
			
			viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

			
		}
		
		
		return viewMatrix;
	}
	// esse metodo calcula o objeto que a camera esta vendo
	public Matrix4f getModelViewMatrix(GameItem gameItem, Matrix4f viewMatrix) {
		Vector3f rotation = gameItem.getRotation();
		modelViewMatrix.identity().translate(gameItem.getPosition())
				.rotate((float) Math.toRadians(-rotation.x), new Vector3f(1, 0, 0))
				.rotate((float) Math.toRadians(-rotation.y), new Vector3f(0, 1, 0))
				.rotate((float) Math.toRadians(-rotation.z), new Vector3f(0, 0, 1))
				.scale(gameItem.getScale());
		Matrix4f viewCurr = new Matrix4f(viewMatrix);
		return viewCurr.mul(modelViewMatrix);
	}

}