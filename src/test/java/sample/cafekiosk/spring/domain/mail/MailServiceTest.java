package sample.cafekiosk.spring.domain.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.api.service.mail.MailService;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;
//    @Spy
//    private MailSendClient mailSendClient;
    /*
    * @Spy를 사용하면 when 대신 do를 써야한다.
    * spy는 실제 객체를 활용하기 때문에 아래처럼 정의해준 경우가 아니라면 실제 메서드를 호출한다.
    *  ex) doReturn(true).when(mailSendClient).sendMail(anyString(),anyString(),anyString(),anyString());
    * */
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;
    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        /*
         * 아래에서  Mockito.mock(MailSendClient.class)로 Mock 객체를 만들 수도 있고
         * @Mock
         * private MailSendClient mailSendClient;
         * 이렇게 테스트 클래스 필드에 선언하여 만들수도 있다. 만약 필드에 선언해서 Mock 객체를 만드려면 클래스에 @ExtendWith(MockitoExtension.class) 어노테이션을 추가해야하 한다.
         * */
        // given
//        MailSendClient mailSendClient = Mockito.mock(MailSendClient.class); // mock 객체를 만들어 준다.
//        MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);

        /*
         * 아래에서  new MailService(mailSendClient, mailSendHistoryRepository)로 Mock 객체를 만들 수도 있고
         * @InjectMocks
         * private MailService mailService;
         * 이렇게 테스트 클래스 필드에 선언하여 만들수도 있다.
         * */
//        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        Mockito.when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);

        //when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        // mailSendHistoryRepository.save 행위가 한번 불렸는지 검증
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("메일 전송 테스트 - BDDMockito(일반 Mockito에서 given절에 when 문법이 들어가는 점을 해결하기 위해 탄생)")
    @Test
    void sendMailWithBDDMockito() {

        // given
        BDDMockito.given(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

        //when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        // mailSendHistoryRepository.save 행위가 한번 불렸는지 검증
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));

        //then
        assertThat(result).isTrue();
    }
}