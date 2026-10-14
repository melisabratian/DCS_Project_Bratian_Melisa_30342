package Project_2;


import Components.*;
import DataObjects.DataFuzzy;
import DataObjects.DataTransfer;
import DataOnly.FLRS;
import DataOnly.FV;
import DataOnly.Fuzzy;
import DataOnly.PlaceNameWithWeight;
import DataOnly.TransferOperation;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.util.ArrayList;

public class Lane {
    public static void main(String[] args) {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Lane Model";
        pn.NetworkPort = 1081;

        FLRS flrsMove = new FLRS(new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PL), new FV(FZ.PM));

        DataFuzzy p_i = new DataFuzzy();
        p_i.SetName("P_i");
        p_i.SetValue(new Fuzzy(0.5F));
        pn.PlaceList.add(p_i);

        DataFuzzy p_l = new DataFuzzy();
        p_l.SetName("P_l");
        pn.PlaceList.add(p_l);

        DataFuzzy lane2 = new DataFuzzy();
        lane2.SetName("lane2");
        pn.PlaceList.add(lane2);

        DataFuzzy p_tl = new DataFuzzy();
        p_tl.SetName("P_TL");
        p_tl.SetValue(new Fuzzy(0.0F));
        pn.PlaceList.add(p_tl);


        DataFuzzy greenConstant = new DataFuzzy();
        greenConstant.SetName("GreenConstant");
        greenConstant.SetValue(new Fuzzy(0.0F));
        pn.ConstantPlaceList.add(greenConstant);

        // Locul de transfer P_out
        DataTransfer p_out = new DataTransfer();
        p_out.SetName("Pout");
        p_out.Value = new TransferOperation("localhost", "1080", "P_i1");
        pn.PlaceList.add(p_out);

        // --- Transition t-in ---
        PetriTransition t_in = new PetriTransition(pn);
        t_in.TransitionName = "t-in";
        t_in.InputPlaceName.add("P_i");

        Condition c_tin = new Condition(t_in, "P_i", TransitionCondition.NotNull);
        GuardMapping grd_tin = new GuardMapping();
        grd_tin.condition = c_tin;

        ArrayList<PlaceNameWithWeight> in_tin = new ArrayList<>();
        in_tin.add(new PlaceNameWithWeight("P_i", 1F));
        ArrayList<String> out_tin = new ArrayList<>();
        out_tin.add("P_l");

        grd_tin.Activations.add(new Activation(t_in, flrsMove, in_tin, TransitionOperation.FLRS, out_tin));
        t_in.GuardMappingList.add(grd_tin);
        pn.Transitions.add(t_in);

        // --- Transition t-send ---
        PetriTransition t_send = new PetriTransition(pn);
        t_send.TransitionName = "t-send";
        t_send.InputPlaceName.add("P_l");

        Condition c_tsend = new Condition(t_send, "P_l", TransitionCondition.NotNull);
        GuardMapping grd_tsend = new GuardMapping();
        grd_tsend.condition = c_tsend;

        ArrayList<PlaceNameWithWeight> in_tsend = new ArrayList<>();
        in_tsend.add(new PlaceNameWithWeight("P_l", 1F));
        ArrayList<String> out_tsend = new ArrayList<>();
        out_tsend.add("lane2");

        grd_tsend.Activations.add(new Activation(t_send, flrsMove, in_tsend, TransitionOperation.FLRS, out_tsend));
        t_send.GuardMappingList.add(grd_tsend);
        pn.Transitions.add(t_send);

        // --- Transition t-out ---
        PetriTransition t_out = new PetriTransition(pn);
        t_out.TransitionName = "t-out";
        t_out.InputPlaceName.add("lane2");
        t_out.InputPlaceName.add("P_TL");

        Condition c_tout1 = new Condition(t_out, "lane2", TransitionCondition.NotNull);
        Condition c_tout2 = new Condition(t_out, "P_TL", TransitionCondition.Equal, "GreenConstant");
        c_tout1.SetNextCondition(LogicConnector.AND, c_tout2);

        GuardMapping grd_tout = new GuardMapping();
        grd_tout.condition = c_tout1;

        grd_tout.Activations.add(new Activation(t_out, "lane2", TransitionOperation.SendOverNetwork, "Pout"));
        t_out.GuardMappingList.add(grd_tout);
        pn.Transitions.add(t_out);

        // Rulare interfata
        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}