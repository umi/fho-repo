package com.example.demo.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateResultDTO {

	private String name;
    private Long sum;
    private Long lose;
    private Double winRate;

    public CalculateResultDTO(String name, Long sum ,Long lose, Double winRate) {

        this.name = name;
        this.sum = sum;
        this.lose = lose;
        this.winRate = winRate;
    }
    
	 public static CalculateResultDTO fromBattleDTO(BattleDTO battleDTO) {
		 
			String name = battleDTO.getName();
			Long sum = battleDTO.getSum();
			Long lose = battleDTO.getLose();
		    Double winRate = sum != 0 ? (double)Math.round((100*(double)lose/(double)sum)*10)/10 : 0;
		 
		 CalculateResultDTO calc = new CalculateResultDTO(name, sum, lose, winRate);
		 
		 return calc;
	 }
}
