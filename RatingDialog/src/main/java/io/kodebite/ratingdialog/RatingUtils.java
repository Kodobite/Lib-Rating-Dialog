package io.kodebite.ratingdialog;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class RatingUtils {


    private static final String PREF_NAME = "rating_pref";
    private static final String LaunchCount = "LaunchCount";
    private static final String IsCounting = "IsCounting";
    private static final String IsShown = "IsShown";
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int count = 0;
    int targetCount;
    Dialog dialog;

    public RatingUtils(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void startLaunchCounting(boolean start) {
        if (start) {
            count = sharedPreferences.getInt(LaunchCount, 0);
            count++;
            editor.putInt(LaunchCount, count);
            editor.apply();

            editor.putBoolean(IsCounting, true);
            editor.apply();

        }
    }

    public void setTargetLaunchCount(int targetCount) {
        this.targetCount = targetCount;
    }

    public void setDialog() {

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.rating_dialogue);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);

        MaterialCardView btnLove = dialog.findViewById(R.id.btnLoveIt);
        MaterialCardView btnClose = dialog.findViewById(R.id.btnLater);
        MaterialCardView btnRate = dialog.findViewById(R.id.btnSubmit);
        MaterialCardView btnImprove = dialog.findViewById(R.id.btnImprove);
        MaterialCardView btnSendFeedback = dialog.findViewById(R.id.btnSendFeedback);
        MaterialCardView btnLater = dialog.findViewById(R.id.btnLaterFeedback);

        TextInputEditText editText = dialog.findViewById(R.id.feedbackMsg);


        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);

        LinearLayout defaultLayout = dialog.findViewById(R.id.defaultRateLayout);
        LinearLayout rateLayout = dialog.findViewById(R.id.rateLayout);
        LinearLayout feedbackLayout = dialog.findViewById(R.id.feedBackLayout);

        ImageView img1 = dialog.findViewById(R.id.ratingImg1);
        ImageView img2 = dialog.findViewById(R.id.ratingImg2);
        ImageView img3 = dialog.findViewById(R.id.ratingImg3);


        if (editText == null) {
            return;
        }
        if (ratingBar == null) {
            return;
        }
        if (btnRate == null) {
            return;
        }
        if (btnSendFeedback == null) {
            return;
        }


        btnLove.setOnClickListener(v -> {
            feedbackLayout.setVisibility(View.GONE);
            defaultLayout.setVisibility(View.GONE);
            rateLayout.setVisibility(View.VISIBLE);
            img1.setVisibility(View.GONE);
            img3.setVisibility(View.GONE);
            img2.setVisibility(View.VISIBLE);
        });

        btnLater.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnClose.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btnImprove.setOnClickListener(v -> {
            defaultLayout.setVisibility(View.GONE);
            rateLayout.setVisibility(View.GONE);
            img1.setVisibility(View.GONE);
            img2.setVisibility(View.GONE);

            feedbackLayout.setVisibility(View.VISIBLE);
            img3.setVisibility(View.VISIBLE);

        });

        btnRate.setOnClickListener(v -> {

            if (ratingBar.getRating() < 5) {
                feedbackLayout.setVisibility(View.VISIBLE);
                img3.setVisibility(View.VISIBLE);
                rateLayout.setVisibility(View.GONE);
                img2.setVisibility(View.GONE);
            } else {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));

                } catch (ActivityNotFoundException e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }

                dialog.dismiss();
                editor.putBoolean(IsShown, true);
                editor.apply();
            }
        });

        btnSendFeedback.setOnClickListener(v -> {
            if (editText.getText().toString().isEmpty()) {
                editText.setError("Please enter your feedback");
            } else if (editText.getText().toString().length() < 20) {
                editText.setError("Please enter at least 20 characters");

            } else {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, "support.xa018bc93@kodebite.io");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                try {
                    intent.putExtra(Intent.EXTRA_TEXT, context.getApplicationInfo().loadLabel(context.getPackageManager())
                            + " " + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
                            + "\n" + editText.getText().toString());
                    context.startActivity(Intent.createChooser(intent, "Send Feedback"));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                editor.putBoolean(IsShown, true);
                editor.apply();
            }
        });

        dialog.setOnDismissListener(dialog -> {
            editor.putBoolean(IsShown, true);
            editor.apply();
        });

        if (sharedPreferences.getBoolean(IsCounting, false)) {
            if (count == targetCount && !sharedPreferences.getBoolean(IsShown, false)) {
                dialog.show();
            }
        } else {
            dialog.show();
        }

    }

    public void showDialog() {
        dialog.show();
    }

}
