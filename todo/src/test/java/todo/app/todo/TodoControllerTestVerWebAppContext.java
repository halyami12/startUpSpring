package todo.app.todo;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

/**
 * Controller Test
 * モッククラスによるServiceメソッドのモック化
 * webAppcontextSetup
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:META-INF/spring/test-con-test.xml",
	"classpath:META-INF/spring/test-mvc.xml"})
@WebAppConfiguration
public class TodoControllerTestVerWebAppContext {
	//applicationContext.xml
	MockMvc mockMvc;
	
	@Inject
	WebApplicationContext wac;
	
	@Before
	public void setUp() throws Exception {
		//standaloneモードでmockMvcを起動
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	//正常動作のパターン
	@Test
	public void testFinish() throws Exception {
		
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
	
	//フォームの中身がBean Validationに引っかかった場合
	@Test
	public void testFinishHasFormErrors() throws Exception {
		
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
		assertEquals("[E002]The requested Todo is already finished. (id=cceae402-c5b1-440f-bae2-7bee19dc17fb)", text);
		
	}

}
