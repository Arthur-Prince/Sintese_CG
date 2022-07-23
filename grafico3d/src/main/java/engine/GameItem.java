package engine;

import org.joml.Vector3f;
import engine.graph.Mesh;

// essa classe representa os objetos que são renderizados
public class GameItem {

	// aqui esta todas as informacoes do objeto como textura, vertices, como interagir com a luz...
	private final Mesh mesh;

	private final Vector3f position;

	private float scale;
	
	// target representa o ponto que o objeto vai ficar girando ao redor
	private Vector3f target;

	// vetor com yaw, pitch e roll
	private final Vector3f rotation;
	
	//velocidade escalar com que o planeta gira ao redor de target
	private float velocidade;

	//distancia do target a posicao
	private float dist;
	
	// velocidade com que o planeta rotaciona em torno de si mesmo
	private float rotAng;

	public GameItem(Mesh mesh) {
		this.mesh = mesh;
		position = new Vector3f();
		this.target = new Vector3f();
		scale = 1;
		rotation = new Vector3f();
		velocidade = 0.005f;
		rotAng = (float) Math.random();
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		this.dist = (float) Math.sqrt(x*x+y*y+z*z);
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}

	public Mesh getMesh() {
		return mesh;
	}
	
	//faz o planeta girar ao redor de target e em torno do proprio eixo
	public void move( float velocidade) {
		this.velocidade += velocidade/dist;
		float aux = (float) (dist*Math.sin(this.velocidade));
		float aux2 = (float) (dist*Math.cos(this.velocidade));
		position.z = aux2+target.z;
		position.x = aux+target.x;
		this.rotation.y += rotAng;

	}

	/**
	 * @return the target
	 */
	public Vector3f getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(Vector3f target) {
		dist = target.distance(position);
		this.target = target;
	}
}