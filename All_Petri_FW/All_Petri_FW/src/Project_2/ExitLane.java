package Project_2;

import Components.*;
import DataObjects.DataFuzzy;
import DataOnly.FLRS;
import DataOnly.FV;
import DataOnly.Fuzzy;
import DataOnly.PlaceNameWithWeight;
import Enumerations.FZ;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.util.ArrayList;

public class ExitLane {
    public static void main(String[] args) {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Exit Lane Model";
        pn.NetworkPort = 1082;

        FLRS flrsMove = new FLRS(new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PL), new FV(FZ.PM));

        DataFuzzy p_o1 = new DataFuzzy();
        p_o1.SetName("P_o1");
        p_o1.SetValue(new Fuzzy(0.2F));
        pn.PlaceList.add(p_o1);

        DataFuzzy p_exit1 = new DataFuzzy();
        p_exit1.SetName("P_Exit1");
        pn.PlaceList.add(p_exit1);

        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "t-1";
        t1.InputPlaceName.add("P_o1");

        Condition c1 = new Condition(t1, "P_o1", TransitionCondition.NotNull);
        GuardMapping g1 = new GuardMapping();
        g1.condition = c1;

        ArrayList<PlaceNameWithWeight> input = new ArrayList<>();
        input.add(new PlaceNameWithWeight("P_o1", 1F));
        ArrayList<String> output = new ArrayList<>();
        output.add("P_Exit1");

        g1.Activations.add(new Activation(t1, flrsMove, input, TransitionOperation.FLRS, output));
        t1.GuardMappingList.add(g1);
        pn.Transitions.add(t1);

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}