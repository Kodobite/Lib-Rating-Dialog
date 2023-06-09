package io.kodebite.ratingdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.FrameLayout;

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

//        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
//
//        EditText editText = dialog.findViewById(R.id.editText);
//
//        MaterialButton submit = dialog.findViewById(R.id.submit_rate);
//
//        MaterialButton cancel = dialog.findViewById(R.id.cancelButton);
//
//        MaterialButton send = dialog.findViewById(R.id.sendButton);
//
//        LinearLayout feedbackLayout = dialog.findViewById(R.id.feedbackButtons);
//
//
//        if (editText == null) {
//            return;
//        }
//        if (ratingBar == null) {
//            return;
//        }
//        if (submit == null) {
//            return;
//        }
//        if (cancel == null) {
//            return;
//        }
//        if (send == null) {
//            return;
//        }
//        if (feedbackLayout == null) {
//            return;
//        }
//
//
//        editText.setVisibility(View.GONE);
//        feedbackLayout.setVisibility(View.GONE);
//
//        submit.setOnClickListener(v -> {
//
//            if (ratingBar.getRating() < 5) {
//                editText.setVisibility(View.VISIBLE);
//                feedbackLayout.setVisibility(View.VISIBLE);
//                submit.setVisibility(View.GONE);
//            } else {
//                try {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
//
//                } catch (ActivityNotFoundException e) {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
//                }
//
//                dialog.dismiss();
//                editor.putBoolean(IsShown, true);
//                editor.apply();
//            }
//        });
//
//        cancel.setOnClickListener(v -> dialog.dismiss());
//
//        send.setOnClickListener(v -> {
//            if (editText.getText().toString().isEmpty()) {
//                editText.setError("Please enter your feedback");
//            } else if (editText.getText().toString().length() < 20) {
//                editText.setError("Please enter at least 20 characters");
//
//            } else {
//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("mailto:"));
//                intent.putExtra(Intent.EXTRA_EMAIL, ConfigurationApp.email);
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for All Recovery");
//                try {
//                    intent.putExtra(Intent.EXTRA_TEXT, context.getApplicationInfo().loadLabel(context.getPackageManager())
//                            + " " + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
//                            + "\n" + editText.getText().toString());
//                    context.startActivity(Intent.createChooser(intent, "Send Feedback"));
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//                dialog.dismiss();
//                editor.putBoolean(IsShown, true);
//                editor.apply();
//            }
//        });

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
