package services;

import com.pds.reservation.model.ReservationDTO;
import com.pds.reservation.repos.ReservationRepository;
import com.pds.reservation.service.ReservationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void testGetReservationDetails() {
        // Mock the repository response
        when(reservationRepository.findAll(org.springframework.data.domain.Sort.by("id"))).thenReturn(Collections.emptyList());
        List<ReservationDTO> reservationDTOList = reservationService.findAll();
        Assertions.assertThat(reservationDTOList).isEmpty();
    }
}