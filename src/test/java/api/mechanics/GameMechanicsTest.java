//package api.mechanics;
//
//import api.mechanics.internal.GameSessionService;
//import api.model.User;
//import api.services.AccountService;
//import api.utils.info.UserCreationInfo;
//import api.websocket.RemotePointService;
//import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
//import org.jetbrains.annotations.NotNull;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.when;
//
///**
// * Created by Vileven on 01.06.17.
// */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@RunWith(SpringRunner.class)
//public class GameMechanicsTest {
//    @MockBean
//    private RemotePointService remotePointService;
//
//    @MockBean
//    private MechanicsExecutor mechanicsExecutor;
//
//    @MockBean
//    private WebSocketServerFactory defaultHandshakeHandler;
//
//    @Autowired
//    private GameMechanics gameMechanics;
//    @Autowired
//    private AccountService accountService;
//    @Autowired
//    private GameSessionService gameSessionService;
//
//    @NotNull
//    private User user1;
//    @NotNull
//    private User user2;
//
//    @Before
//    public void setUp () {
//        when(remotePointService.isConnected(any())).thenReturn(true);
//
//        user1 = accountService.createUser(new UserCreationInfo("login1", "email@mailmail.ru", "123"));
//        user2 = accountService.createUser(new UserCreationInfo("login2", "email@mailmai2.ru", "123"));
//    }
//
//
//}