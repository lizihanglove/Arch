package site.lizihanglove.example;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import site.lizihanglove.arch.ArchView;

public class MainActivity extends AppCompatActivity {

    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArchView stepView = findViewById(R.id.stepView);
        stepView.setMaxNumber(1000);


        valueAnimator = ObjectAnimator.ofInt(0, 1000);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                stepView.setCurrentNumber(current);
                stepView.setText(Integer.toString(current)+"/"+1000);
            }
        });
        valueAnimator.start();
    }

    public void refresh(View view) {
        valueAnimator.start();
    }
}
