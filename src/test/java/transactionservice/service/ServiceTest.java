package transactionservice.service;

import org.example.transactionservice.model.dto.TransactionDto;
import org.example.transactionservice.model.dto.TransactionRequest;
import org.example.transactionservice.model.dto.TransactionResponse;
import org.example.transactionservice.model.entity.TransactionType;
import org.example.transactionservice.repository.TransactionRepository;
import org.example.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    @Test
    void shouldConvertEntityToDtoWithCorrectFormatting() {
        org.example.transactionservice.model.entity.Transaction t = new org.example.transactionservice.model.entity.Transaction();
        t.setTransactionId("968953739");
        t.setAccountId("1561887");
        t.setAmount(new BigDecimal("602871.09"));
        t.setDateTime(LocalDateTime.parse("2022-12-11T01:41:17"));
        t.setType(TransactionType.INCOME);

        TransactionDto dto = service.toDto(t);

        assertAll(
                () -> assertEquals("INCOME", dto.getType()),
                () -> assertEquals("2022-12-11T01:41:17", dto.getDateTime().toString()),
                () -> assertEquals(602871.09, dto.getAmount().doubleValue())
        );
    }

//    @Test
//    void shouldHandleEmptyResultSet() {
//        when(repository.findByFilters(any(), any(), any(), any()))
//                .thenReturn(Page.empty());
//
//        TransactionRequest request = new TransactionRequest();
//        request.setPage(0);
//        request.setSize(10);
//
//        TransactionResponse response = service.getTransactions(request);
//
//        assertTrue(response.getTransactions().isEmpty());
//        assertEquals(0, response.getTotalItems());
//    }
}