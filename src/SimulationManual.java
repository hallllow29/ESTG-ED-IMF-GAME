import entities.Room;

public class SimulationManual {

	private final Room entry_point;


	// A simulação manual deve pedir ao Tó Cruz a qual a entrada a considerar...
	public SimulationManual(Room entry_point) {
		this.entry_point = entry_point;
	}
}

/*
A simulação manual deve pedir ao Tó Cruz a qual a entrada a considerar e a partir daí,


 iterativamente, pedir a divisão para a qual deseja movimentar-se.
  Quando chegar ao alvo deve avisar o Tó Cruz.
Quando o Tó Cruz entra num aposento que tem um criminoso, os pontos de poder do criminoso
devem ser retirados aos pontos de vida do Tó. A missão termina com sucesso quando o Tó saí do
edifício com o alvo. A missão termina sem sucesso quando o Tó perde todos os seus pontos de vida,
ou sai do edifício sem o alvo. Graças a um sofisticado sistema de espionagem, o Tó Cruz tem
conhecimento dos inimigos/kits de recuperação/coletes em cada divisão em tempo real. No final
de cada turno, deve ser apresentado ao Tó Cruz o melhor caminho para o alvo e para o kit de
recuperação mais próximo.
 */
