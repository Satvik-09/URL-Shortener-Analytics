package tests;

import com.satvik.url_shortner.entity.UrlMapping;
import com.satvik.url_shortner.repository.UrlRepository;
import com.satvik.url_shortner.service.UrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @Test
    void shouldGeneratecodeforUrl(){
        UrlMapping savedMapping = new UrlMapping();
        savedMapping.setId(1L);
        savedMapping.setOriginalUrl("https://example.com");


        when(urlRepository.save(any(UrlMapping.class))).thenReturn(savedMapping);

        String shortCode = urlService.shortenurl("https;//example.com");
         assertEquals("1",shortCode);
         verify(urlRepository,times(2)).save( any(UrlMapping.class));
    }

    @Test
    void shouldThrowWhenShortcodenotFound(){
        when(urlRepository.findByShortcode("999")).thenReturn(java.util.Optional.empty());

         assertThrows(RuntimeException.class,() -> urlService.getOriginalurl("999"));
    }
}
