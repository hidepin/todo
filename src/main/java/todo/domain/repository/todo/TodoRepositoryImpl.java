package todo.domain.repository.todo;

import java.util.Collection;

import todo.domain.model.Todo;

public class TodoRepositoryImpl implements TodoRepository {

	@Override
	public Todo findOne(String todoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Todo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Todo todo) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean update(Todo todo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(Todo todo) {
		// TODO Auto-generated method stub

	}

	@Override
	public long countByFinished(boolean finished) {
		// TODO Auto-generated method stub
		return 0;
	}

}
