package com.example.tweaty.gesturestest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InstructionTapFragment extends Fragment {

    public static InstructionTapFragment newInstance() {
        InstructionTapFragment fragment = new InstructionTapFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instruction_tap, container, false);
    }

}
