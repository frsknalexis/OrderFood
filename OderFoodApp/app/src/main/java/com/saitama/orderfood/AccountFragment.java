package com.saitama.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;

import java.util.Objects;

public class AccountFragment extends Fragment {
    private View view;
    private ConstraintLayout bill, user;
    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        find();
        setHistory();
        setUser();
        logout();
        return view;
    }

    public void find() {
        bill = view.findViewById(R.id.item_bill);
        user = view.findViewById(R.id.item_account);
        btnLogout = view.findViewById(R.id.btn_logout);
    }

    public void setHistory() {
        bill.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), OrderActivity.class);
            Objects.requireNonNull(getContext()).startActivity(intent);
        });
    }

    public void setUser() {
        user.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RegisterActivity.class);
            Objects.requireNonNull(getContext()).startActivity(intent);
        });
    }

    private void logout() {
        this.btnLogout.setOnClickListener(v -> {
            SharedPrefsUtil.getInstance().remove(ContainsUtil.LOGIN);
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            Objects.requireNonNull(this.getActivity()).finish();
        });
    }
}