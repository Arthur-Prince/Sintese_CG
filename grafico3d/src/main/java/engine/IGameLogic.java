package engine;

// o basico que seria preciso implementar para rodar o joguinho
public interface IGameLogic {

	void init(Window window) throws Exception;

	void input(Window window, MouseInput mouseInput);

	void update(float interval, MouseInput mouseInput);

	void render(Window window);

	void cleanup();
}