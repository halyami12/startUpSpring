package todo.domain.service.todo;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import todo.domain.model.Todo;
import todo.domain.repository.todo.TodoRepository;

public class TestFinishNGMock implements TodoRepository{

	@Override
	public Todo findOne(String todoId) {
		
		Todo todo = new Todo();
		todo.setTodoId("cceae402-c5b1-440f-bae2-7bee19dc17fb");
		todo.setTodoTitle("one");
		todo.setFinished(true);
		
		try {
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String strDate = "2017-10-01 15:39:17.888";
			Date date1 = sdFormat.parse(strDate);
			todo.setCreatedAt(date1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return todo;
	}

	@Override
	public Collection<Todo> findAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void create(Todo todo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	//trueを返すモック
	@Override
	public boolean update(Todo todo) {
		return true;
	}

	@Override
	public void delete(Todo todo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public long countByFinished(boolean finished) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
