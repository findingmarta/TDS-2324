package com.ruirua.sampleguideapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ruirua.sampleguideapp.databinding.ActivityProfileBinding;
import com.ruirua.sampleguideapp.viewModel.UserViewModel;
/*
public class ProfileFragment extends Fragment {

    private ActivityProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserViewModel profileViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);

        binding = ActivityProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView profileName = binding.profileName;
        profileViewModel.getText().observe(getViewLifecycleOwner(), profileName::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}*/