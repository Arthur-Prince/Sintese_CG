package engine.graph;

import org.joml.Vector3f;


public class Camera {

	private final Vector3f position;

	private final Vector3f rotation;
	
	private final Vector3f target;
	
	private final Vector3f cameraDirection;
	
	private final Vector3f rightAxis;
	
	private final Vector3f upAxis;
	
	private boolean haveTarget;

	public Camera() {
		position = new Vector3f(0,16,0);
		rotation = new Vector3f();
		target = new Vector3f();
		cameraDirection = new Vector3f();
		rightAxis = new Vector3f();
		upAxis = new Vector3f();
		setHaveTarget(false);
	}

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
		target = new Vector3f();
		cameraDirection = new Vector3f();
		rightAxis = new Vector3f();
		upAxis = new Vector3f();
		setHaveTarget(false);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public void movePosition(float offsetX, float offsetY, float offsetZ) {
		if (offsetZ != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
			position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
		}
		if (offsetX != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
			position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
		}
		position.y += offsetY;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
		
	}

	public void moveRotation(float offsetX, float offsetY, float offsetZ) {
		rotation.x += offsetX;
		rotation.y += offsetY;
		rotation.z += offsetZ;

	}
	
	public void translacao(float offsetX, float offsetY, float offsetZ) {
		moveRotation(offsetX, offsetY, offsetZ);
		
		float dist = position.distance(target);
		Vector3f aux = new Vector3f(position);
		aux.sub(position);
		aux.add(target);
		aux.rotateX((float) Math.toRadians(offsetX*dist));
		aux.rotateY((float) Math.toRadians(offsetY*dist));
		
		aux.sub(target);
		aux.add(position);
		
		int frenteAtras = 0;
		float novaDist = position.distance(target);
		
		if(novaDist-dist >= 0) {
			frenteAtras = -1;
		}else {
			frenteAtras = 1;
		}
		aux.z = frenteAtras*(float)Math.sqrt(dist*dist - ((aux.x*aux.x)+(aux.y*aux.y)));
		
		position.set(aux);


		
		
	}
	public Vector3f getTarget() {
		return target;
	}
	public void setTarget(float offsetX, float offsetY, float offsetZ) {
		target.x = offsetX;
		target.y = offsetY;
		target.z = offsetZ;
		setHaveTarget(true);
	}

	public boolean haveTarget() {
		return haveTarget;
	}

	public void setHaveTarget(boolean haveTarget) {
		this.haveTarget = haveTarget;
	}
	
	public void lookAt() {
		rotation.set(position.sub(target));
		
		
	}

	/**
	 * @return the rightAxis
	 */
	public Vector3f getRightAxis() {
		return rightAxis;
	}

	/**
	 * @return the cameraDirection
	 */
	public Vector3f getCameraDirection() {
		return cameraDirection;
	}

	/**
	 * @return the upAxis
	 */
	public Vector3f getUpAxis() {
		return upAxis;
	}
}