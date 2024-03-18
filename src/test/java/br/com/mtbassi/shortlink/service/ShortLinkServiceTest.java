package br.com.mtbassi.shortlink.service;

import br.com.mtbassi.shortlink.domain.ShortLink;
import br.com.mtbassi.shortlink.dto.RequestDTO;
import br.com.mtbassi.shortlink.dto.ResponseDTO;
import br.com.mtbassi.shortlink.repository.ShortLinkRepository;
import br.com.mtbassi.shortlink.stub.ShortLinkStub;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class ShortLinkServiceTest {

    @InjectMocks
    private ShortLinkService service;

    @Mock
    private ShortLinkRepository shortLinkRepository;

    @Mock
    private ModelMapper modelMapper;

    private RequestDTO requestDTO;


    @BeforeEach
    void setup() {
        requestDTO = new RequestDTO();
        requestDTO.setOriginalLink("https://github.com/mtbassi");
        RequestAttributes attributes = new ServletRequestAttributes(Mockito.mock(HttpServletRequest.class));
        RequestContextHolder.setRequestAttributes(attributes);
    }

    @AfterEach
    void cleanup() {
        RequestContextHolder.resetRequestAttributes();
    }


    @Test
    void shouldReturnShortenedUrlForValidLink() {
        ShortLink shortLinkEntity = ShortLinkStub.valid();
        when(shortLinkRepository.save(any(ShortLink.class))).thenReturn(shortLinkEntity);
        when(modelMapper.map(eq(shortLinkEntity), eq(ResponseDTO.class))).thenReturn(ShortLinkStub.responseDTO());
        var result = service.shortenLink(requestDTO);
        assertNotNull("Return is null", result);
        assertEquals("Short link differs from expected.", "/shortlink/MTBASSI", result.getShortLink());
        assertEquals("Short original link differs from expected.", "https://github.com/mtbassi", result.getOriginalLink());
        assertEquals("Number of accesses differs from expected.", 0, result.getAccess());
        assertEquals("Creation date differs from expected.", LocalDateTime.of(2000, 8, 12, 0, 0, 0), result.getCreationDate());
        verify(modelMapper).map(eq(shortLinkEntity), eq(ResponseDTO.class));
        verifyNoMoreInteractions(modelMapper);
    }
}
