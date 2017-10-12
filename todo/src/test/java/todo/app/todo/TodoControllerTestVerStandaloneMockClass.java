package todo.app.todo;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

import todo.domain.service.todo.TodoService;

/**
 * Controller Test
 * モッククラスによるServiceメソッドのモック化
 * standaloneSetup
 */
public class TodoControllerTestVerStandaloneMockClass {

	TodoController target;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		//モッククラスをテストによって切り替えるため処理なし
	}
	
	//正常動作のパターン
	@Test
	public void testFinish() throws Exception {
		
		//Serviceのモック化
		target = new TodoController();
		TodoService mockService = new TestFinishMock();
		target.todoService = mockService;
		
		//standaloneモードでmockMvcを起動
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		
		//Controllerに投げるリクエストを作成
		MockHttpServletRequestBuilder getRequest = 
				MockMvcRequestBuilders.post("/todo/finish")
										.param("todoId", "cceae402-c5b1-440f-bae2-7bee19dc17fb")
										.param("todoTitle", "one");
		
		//Controllerにリクエストを投げる
		ResultActions results = mockMvc.perform(getRequest);
		
		//結果検証
		results.andDo(print());
		results.andExpect(status().isFound());
		results.andExpect(view().name("redirect:/todo/list"));
		
		//FlashMapのデータを取得(model.addFlashAttributeに設定したmessageオブジェクトのtextを取得する。半ば強引)
		FlashMap flashMap = results.andReturn().getFlashMap();
		Collection<Object> collection = flashMap.values();
		for(Object obj : collection) {
			ResultMessages messages = (ResultMessages) obj;
			ResultMessage message = messages.getList().get(0);
			String text = message.getText();
			assertEquals("Finished successfully!", text);
		}
	}
	
	//serviceメソッドが異常を返した場合
	@Test
	public void testFinishThrowException() throws Exception {
		
		//Serviceのモック化
		target = new TodoController();
		TodoService mockService = new TestFinishExceptionMock();
		target.todoService = mockService;
		
		///standaloneモードでmockMvcを起動
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		
		//Controllerに投げるリクエストを作成
		MockHttpServletRequestBuilder getRequest = 
				MockMvcRequestBuilders.post("/todo/finish")
										.param("todoId", "cceae402-c5b1-440f-bae2-7bee19dc17fb")
										.param("todoTitle", "one");
		
		//Controllerにリクエストを投げる
		ResultActions results = mockMvc.perform(getRequest);
		
		//結果検証
		results.andDo(print());
		results.andExpect(status().isOk());
		results.andExpect(view().name("todo/list"));
		
		//model Confirmation
		ModelAndView mav = results.andReturn().getModelAndView();
		
		ResultMessages actResultMessages = (ResultMessages)mav.getModel().get("resultMessages");
		ResultMessage actResultMessage = actResultMessages.getList().get(0);
		String text = actResultMessage.getText();
		assertEquals("[E004]The requested Todo is not found. (id=cceae402-c5b1-440f-bae2-7bee19dc17fb)", text);
	}
	
	//フォームの中身がBean Validationに引っかかった場合
	@Test
	public void testFinishHasFormErrors() throws Exception {
		
		//standAloneSetupモードだとspring-testでもフォームのエラーでのテストができない・・？
		
		/*
		//Serviceのモック化
		target = new TodoController();
		TodoService mockService = new TestFinishMock();
		target.todoService = mockService;
		
		///standaloneモードでmockMvcを起動
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
		
		//Controllerに投げるリクエストを作成
		MockHttpServletRequestBuilder getRequest = 
				MockMvcRequestBuilders.post("/todo/finish")
										.param("todoId", "cceae402-c5b1-440f-bae2-7bee19dc17fb")
										.param("todoTitle", "");
		//Controllerにリクエストを投げる
		ResultActions results = mockMvc.perform(getRequest);
		
		//結果検証
		results.andDo(print());
		results.andExpect(status().isOk());
		results.andExpect(view().name("todo/list"));
		
		//model Confirmation
		ModelAndView mav = results.andReturn().getModelAndView();
		
		ResultMessages actResultMessages = (ResultMessages)mav.getModel().get("resultMessages");
		ResultMessage actResultMessage = actResultMessages.getList().get(0);
		String text = actResultMessage.getText();
		assertEquals("", text);
		*/
		
	}
}
