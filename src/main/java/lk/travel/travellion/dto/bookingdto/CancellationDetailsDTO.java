package lk.travel.travellion.dto.bookingdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CancellationDetailsDTO {

    private String cancellationScheme;
    private BigDecimal cancellationAmount;
}
