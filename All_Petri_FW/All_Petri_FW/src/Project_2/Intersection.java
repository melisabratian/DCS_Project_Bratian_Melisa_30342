package Project_2;

import Components.*;
import DataObjects.DataFuzzy;
import DataOnly.FLRS;
import DataOnly.FV;
import DataOnly.Fuzzy;
import DataOnly.PlaceNameWithWeight;
import Enumerations.FZ;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

import java.util.ArrayList;

public class Intersection {
    public static void main(String[] args) {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Intersection Model";
        pn.NetworkPort = 1080;

        FLRS flrsPlus = new FLRS(
                new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR),
                new FV(FZ.NL), new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM),
                new FV(FZ.NL), new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL),
                new FV(FZ.NM), new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), new FV(FZ.PL),
                new FV(FZ.ZR), new FV(FZ.PM), new FV(FZ.PL), new FV(FZ.PL), new FV(FZ.PL)
        );

        DataFuzzy p_i1 = new DataFuzzy(); p_i1.SetName("P_i1"); p_i1.SetValue(new Fuzzy(0.2F)); pn.PlaceList.add(p_i1);
        DataFuzzy p_i2 = new DataFuzzy(); p_i2.SetName("P_i2"); p_i2.SetValue(new Fuzzy(0.3F)); pn.PlaceList.add(p_i2);
        DataFuzzy p_i3 = new DataFuzzy(); p_i3.SetName("P_i3"); p_i3.SetValue(new Fuzzy(0.1F)); pn.PlaceList.add(p_i3);
        DataFuzzy p_i4 = new DataFuzzy(); p_i4.SetName("P_i4"); p_i4.SetValue(new Fuzzy(0.2F)); pn.PlaceList.add(p_i4);

        DataFuzzy p_temp1 = new DataFuzzy(); p_temp1.SetName("P_temp1"); pn.PlaceList.add(p_temp1);
        DataFuzzy p_temp2 = new DataFuzzy(); p_temp2.SetName("P_temp2"); pn.PlaceList.add(p_temp2);
        DataFuzzy p_i = new DataFuzzy(); p_i.SetName("P_i"); pn.PlaceList.add(p_i);

        DataFuzzy c_025 = new DataFuzzy(); c_025.SetName("C_025"); c_025.SetValue(new Fuzzy(0.25F)); pn.ConstantPlaceList.add(c_025);
        DataFuzzy c_050 = new DataFuzzy(); c_050.SetName("C_050"); c_050.SetValue(new Fuzzy(0.50F)); pn.ConstantPlaceList.add(c_050);
        DataFuzzy c_012_1 = new DataFuzzy(); c_012_1.SetName("C_012_1"); c_012_1.SetValue(new Fuzzy(0.12F)); pn.ConstantPlaceList.add(c_012_1);
        DataFuzzy c_012_2 = new DataFuzzy(); c_012_2.SetName("C_012_2"); c_012_2.SetValue(new Fuzzy(0.12F)); pn.ConstantPlaceList.add(c_012_2);

        DataFuzzy p_o1 = new DataFuzzy(); p_o1.SetName("P_o1"); pn.PlaceList.add(p_o1);
        DataFuzzy p_o2 = new DataFuzzy(); p_o2.SetName("P_o2"); pn.PlaceList.add(p_o2);
        DataFuzzy p_o3 = new DataFuzzy(); p_o3.SetName("P_o3"); pn.PlaceList.add(p_o3);
        DataFuzzy p_o4 = new DataFuzzy(); p_o4.SetName("P_o4"); pn.PlaceList.add(p_o4);

        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "t1";
        t1.InputPlaceName.add("P_i1"); t1.InputPlaceName.add("P_i2");
        Condition c1 = new Condition(t1, "P_i1", TransitionCondition.NotNull);
        Condition c2 = new Condition(t1, "P_i2", TransitionCondition.NotNull);
        c1.SetNextCondition(LogicConnector.AND, c2);
        GuardMapping g1 = new GuardMapping(); g1.condition = c1;
        ArrayList<PlaceNameWithWeight> in1 = new ArrayList<>();
        in1.add(new PlaceNameWithWeight("P_i1", 1F)); in1.add(new PlaceNameWithWeight("P_i2", 1F));
        ArrayList<String> out1 = new ArrayList<>(); out1.add("P_temp1");
        g1.Activations.add(new Activation(t1, flrsPlus, in1, TransitionOperation.FLRS, out1));
        t1.GuardMappingList.add(g1); pn.Transitions.add(t1);

        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "t2";
        t2.InputPlaceName.add("P_i3"); t2.InputPlaceName.add("P_i4");
        Condition c3 = new Condition(t2, "P_i3", TransitionCondition.NotNull);
        Condition c4 = new Condition(t2, "P_i4", TransitionCondition.NotNull);
        c3.SetNextCondition(LogicConnector.AND, c4);
        GuardMapping g2 = new GuardMapping(); g2.condition = c3;
        ArrayList<PlaceNameWithWeight> in2 = new ArrayList<>();
        in2.add(new PlaceNameWithWeight("P_i3", 1F)); in2.add(new PlaceNameWithWeight("P_i4", 1F));
        ArrayList<String> out2 = new ArrayList<>(); out2.add("P_temp2");
        g2.Activations.add(new Activation(t2, flrsPlus, in2, TransitionOperation.FLRS, out2));
        t2.GuardMappingList.add(g2); pn.Transitions.add(t2);

        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "t3";
        t3.InputPlaceName.add("P_temp1"); t3.InputPlaceName.add("P_temp2");
        Condition c5 = new Condition(t3, "P_temp1", TransitionCondition.NotNull);
        Condition c6 = new Condition(t3, "P_temp2", TransitionCondition.NotNull);
        c5.SetNextCondition(LogicConnector.AND, c6);
        GuardMapping g3 = new GuardMapping(); g3.condition = c5;
        ArrayList<PlaceNameWithWeight> in3 = new ArrayList<>();
        in3.add(new PlaceNameWithWeight("P_temp1", 1F)); in3.add(new PlaceNameWithWeight("P_temp2", 1F));
        ArrayList<String> out3 = new ArrayList<>(); out3.add("P_i");
        g3.Activations.add(new Activation(t3, flrsPlus, in3, TransitionOperation.FLRS, out3));
        t3.GuardMappingList.add(g3); pn.Transitions.add(t3);

        PetriTransition t_s = new PetriTransition(pn);
        t_s.TransitionName = "t-s";
        t_s.InputPlaceName.add("P_i");
        Condition c_ts = new Condition(t_s, "P_i", TransitionCondition.NotNull);
        GuardMapping g_ts = new GuardMapping(); g_ts.condition = c_ts;

        ArrayList<PlaceNameWithWeight> in_s1 = new ArrayList<>();
        in_s1.add(new PlaceNameWithWeight("P_i", 1F)); in_s1.add(new PlaceNameWithWeight("C_025", 1F));
        g_ts.Activations.add(new Activation(in_s1, TransitionOperation.Prod_Fuzzy, "P_o1", t_s));

        ArrayList<PlaceNameWithWeight> in_s2 = new ArrayList<>();
        in_s2.add(new PlaceNameWithWeight("P_i", 1F)); in_s2.add(new PlaceNameWithWeight("C_050", 1F));
        g_ts.Activations.add(new Activation(in_s2, TransitionOperation.Prod_Fuzzy, "P_o2", t_s));

        ArrayList<PlaceNameWithWeight> in_s3 = new ArrayList<>();
        in_s3.add(new PlaceNameWithWeight("P_i", 1F)); in_s3.add(new PlaceNameWithWeight("C_012_1", 1F));
        g_ts.Activations.add(new Activation(in_s3, TransitionOperation.Prod_Fuzzy, "P_o3", t_s));

        ArrayList<PlaceNameWithWeight> in_s4 = new ArrayList<>();
        in_s4.add(new PlaceNameWithWeight("P_i", 1F)); in_s4.add(new PlaceNameWithWeight("C_012_2", 1F));
        g_ts.Activations.add(new Activation(in_s4, TransitionOperation.Prod_Fuzzy, "P_o4", t_s));

        t_s.GuardMappingList.add(g_ts); pn.Transitions.add(t_s);

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}