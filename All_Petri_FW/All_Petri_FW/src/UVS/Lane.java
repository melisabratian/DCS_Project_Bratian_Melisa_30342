package UVS;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Lane {
	public static void main(String[] args) {

		PetriNet pn = new PetriNet();
		pn.PetriNetName = "Lane Model";
		pn.NetworkPort = 1080;

		// Loc constant pentru semnalul Verde (convenție fuzzy)[cite: 6, 7]
		DataString green = new DataString();
		green.Printable = false;
		green.SetName("green");
		green.SetValue("<0, 0, 1, 0, 0>");
		pn.ConstantPlaceList.add(green);

		DataString full = new DataString();
		full.SetName("full");
		full.SetValue("full");
		pn.ConstantPlaceList.add(full);

		DataCar p1 = new DataCar();
		p1.SetName("P_i");
		pn.PlaceList.add(p1);

		DataCarQueue p2 = new DataCarQueue();
		p2.Value.Size = 3;
		p2.SetName("P_l");
		pn.PlaceList.add(p2);

		DataString p3 = new DataString();
		p3.SetName("P_TL");
		pn.PlaceList.add(p3);

		DataTransfer pOut = new DataTransfer();
		pOut.SetName("P_out");
		pOut.Value = new TransferOperation("localhost", "1080", "P_I");
		pn.PlaceList.add(pOut);

		DataCar p4 = new DataCar();
		p4.SetName("P_b");
		pn.PlaceList.add(p4);

		PetriTransition tIn = new PetriTransition(pn);
		tIn.TransitionName = "t-in";
		tIn.InputPlaceName.add("P_i");
		tIn.InputPlaceName.add("P_l");

		Condition tInCt1 = new Condition(tIn, "P_i", TransitionCondition.NotNull);
		Condition tInCt2 = new Condition(tIn, "P_l", TransitionCondition.CanAddCars);
		tInCt1.SetNextCondition(LogicConnector.AND, tInCt2);

		GuardMapping grdTIn = new GuardMapping();
		grdTIn.condition = tInCt1;
		grdTIn.Activations.add(new Activation(tIn, "P_i", TransitionOperation.AddElement, "P_l"));
		tIn.GuardMappingList.add(grdTIn);

		tIn.Delay = 0;
		pn.Transitions.add(tIn);

		PetriTransition tSend = new PetriTransition(pn);
		tSend.TransitionName = "t-send";
		tSend.InputPlaceName.add("P_l");
		tSend.InputPlaceName.add("P_TL");

		Condition tSendCt1 = new Condition(tSend, "P_TL", TransitionCondition.Equal, "green");
		Condition tSendCt2 = new Condition(tSend, "P_l", TransitionCondition.HaveCar);
		tSendCt1.SetNextCondition(LogicConnector.AND, tSendCt2);

		GuardMapping grdTSend = new GuardMapping();
		grdTSend.condition = tSendCt1;
		grdTSend.Activations.add(new Activation(tSend, "P_l", TransitionOperation.PopElementWithoutTarget, "P_b"));
		grdTSend.Activations.add(new Activation(tSend, "P_TL", TransitionOperation.Move, "P_TL"));
		// Trimiterea datelor prin rețea folosind P_out către intersecție[cite: 7]
		grdTSend.Activations.add(new Activation(tSend, "P_b", TransitionOperation.SendOverNetwork, "P_out"));

		tSend.GuardMappingList.add(grdTSend);
		tSend.Delay = 0;
		pn.Transitions.add(tSend);

		System.out.println("Lane Model started \n ------------------------------");
		pn.Delay = 2000;

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
	}
}