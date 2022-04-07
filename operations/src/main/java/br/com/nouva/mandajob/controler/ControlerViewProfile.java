package br.com.nouva.mandajob.controler;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.google.gson.Gson;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import br.com.nouva.mandajob.R;
import br.com.nouva.mandajob.helper.ApiGetService;
import br.com.nouva.mandajob.model.ModelOverview;
import br.com.nouva.mandajob.model.ModelUrls;
import br.com.nouva.mandajob.model.Overview;
import br.com.nouva.mandajob.model.Urls;
import br.com.nouva.mandajob.service.Service;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

public class ControlerViewProfile {

    private static int SELECTED_TAB = 1;
    private static boolean CONTENT_ON;
    public Context context;
    public DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory
            .Builder().setCrossFadeEnabled(true).build();
    private int angle = 0;
    private ImageView arrow, iv_recent_one, iv_recent_two;
    private CircleImageView iv_profile;
    private TextView tv_name, tv_pin, tv_bio;
    private TextView tv_button_work_show, tv_button_about_show;
    private ProgressBar progress_bar_profile_view, progress_bar_profile_master;
    private RelativeLayout layout_profile_user;
    private LinearLayout layout_information_show, layout_button_work_show, layout_button_about_show;
    private GridLayout grid_tab_profile;
    private DecoView decoView;
    private CardView cv_button_show_info;

    public ControlerViewProfile(Context context, View view) {
        this.context = context;
        openIDS(view);
    }

    private void openIDS(View view) {
        CONTENT_ON = false;
        iv_profile = view.findViewById(R.id.iv_profile_user);

        tv_name = view.findViewById(R.id.tv_name_user_profile);
        tv_pin = view.findViewById(R.id.tv_label_pin);
        tv_bio = view.findViewById(R.id.tv_label_bio);

        tv_name.setText("");
        tv_bio.setText("");

        progress_bar_profile_view = view.findViewById(R.id.progress_bar_profile_view);
        progress_bar_profile_master = view.findViewById(R.id.progress_bar_profile_master);

        layout_profile_user = view.findViewById(R.id.layout_profile_user);
        cv_button_show_info = view.findViewById(R.id.cv_button_show_info);

        layout_information_show = view.findViewById(R.id.layout_information_show);
        layout_information_show.setVisibility(View.GONE);

        layout_button_work_show = view.findViewById(R.id.layout_button_work_show);
        layout_button_about_show = view.findViewById(R.id.layout_button_about_show);

        tv_button_work_show = view.findViewById(R.id.tv_button_work_show);
        tv_button_about_show = view.findViewById(R.id.tv_button_about_show);

        grid_tab_profile = view.findViewById(R.id.grid_tab_profile);

        arrow = view.findViewById(R.id.iv_icon_show_info);
        iv_recent_one = view.findViewById(R.id.iv_recent_one);
        iv_recent_two = view.findViewById(R.id.iv_recent_two);

        changeTabSelection(SELECTED_TAB);

        decoView = view.findViewById(R.id.dynamicArcView);
        SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#2c2c31"))
                .setRange(0, 50, 0)
                .build();

        int backIndex = decoView.addSeries(seriesItem);

        decoView.addEvent(new DecoEvent.Builder(50)
                .setIndex(backIndex)
                .build());

        initProgressAnim();

        progress_bar_profile_master.setVisibility(View.VISIBLE);
        layout_profile_user.setVisibility(View.GONE);

    }

    private void initProgressAnim() {
        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#ea5551"))
                .setRange(0, 50, 0)
                .build();

        int series1Index = decoView.addSeries(seriesItem);

        decoView.addEvent(new DecoEvent.Builder(36.3f)
                .setIndex(series1Index)
                .setDelay(1000)
                .build());
    }

    public void initializeService(final String key) {
        Service service = ApiGetService.getRetrofitInstance().create(Service.class);
        Overview overview = new Overview(key);

        service.seeOverview(overview).enqueue(new Callback<List<ModelOverview>>() {
            @Override
            public void onResponse(@NonNull Call<List<ModelOverview>> call, @NonNull Response<List<ModelOverview>> response) {
                if (response.code() == 200) {
                    getUrlsAssets(key, response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ModelOverview>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Problem to connect to server...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUrlsAssets(String key, final Response<List<ModelOverview>> json) {
        Service service = ApiGetService.getRetrofitInstance().create(Service.class);
        Urls urls = new Urls(key);

        service.getUrls(urls).enqueue(new Callback<List<ModelUrls>>() {
            @Override
            public void onResponse(@NonNull Call<List<ModelUrls>> call, @NonNull Response<List<ModelUrls>> response) {
                if (response.code() == 200) {
                    createInfosExtra(Objects.requireNonNull(json.body()));
                    Gson gson = new Gson();
                    String over = gson.toJson(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ModelUrls>> call, @NonNull Throwable t) {

            }
        });
    }

    private void createInfosExtra(List<ModelOverview> body) {

        for (int i = 0; i < body.size(); i++) {
            ModelOverview overview = new ModelOverview(body.get(i).getContent(), body.get(i).getCreated(),
                    body.get(i).getId_project(), body.get(i).getId_user(), body.get(i).getName(), body.get(i).getProfile(),
                    body.get(i).getTitle(), body.get(i).getType(), body.get(i).getWallpaper());

            String name = overview.getName();
            int start = name.indexOf(' ');
            if (start >= 0) name = "Pinned from " + name.substring(0, start);

            String category = overview.getType();
            if (category.equals("Development")) category = "Dev";

            String firstName = overview.getName();
            if (firstName.split("\\w+").length > 1)
                firstName = overview.getName().substring(0, overview.getName().lastIndexOf(' '));
            else
                firstName = overview.getName();

            tv_name.setText(String.format("%s, 29", firstName));
            //tv_pin.setText("Pinned");
            tv_bio.setText("''" + randomStringBio() + "''");
            makeTextViewResizable(tv_bio, 3, " ");

            initializeImagesAssets(overview.getProfile());

        }
    }

    private void initializeImagesAssets(String profile) {
        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade(factory))
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(512, 512))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        progress_bar_profile_view.setVisibility(View.GONE);
                        iv_profile.setImageBitmap(resource);
                        return false;
                    }
                })
                .load(profile)
                .into(iv_profile);

        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade(factory))
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(512, 512))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        iv_recent_one.setImageBitmap(resource);
                        return false;
                    }
                })
                .load(context.getString(R.string.url_asset_image_plants_girl_balance))
                .into(iv_recent_one);

        Glide.with(context)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(withCrossFade(factory))
                .apply(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(512, 512))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        iv_recent_two.setImageBitmap(resource);
                        return false;
                    }
                })
                .load(context.getString(R.string.url_asset_image_plants_girl_hug))
                .into(iv_recent_two);

        progress_bar_profile_master.setVisibility(View.GONE);
        layout_profile_user.setVisibility(View.VISIBLE);
    }

    public void changeTabSelection(int item) {
        if (item != SELECTED_TAB) {
            Animation animationIn = AnimationUtils.loadAnimation(context, R.anim.fadein);
            Animation animationOut = AnimationUtils.loadAnimation(context, R.anim.fadeout);

            if (item == 1) {
                layout_button_work_show.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_selected_bar_profile_secondary));
                tv_button_work_show.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                layout_button_work_show.startAnimation(animationIn);

                grid_tab_profile.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_label_bar_profile_secondary));

                layout_button_about_show.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_no_selected_bar_profile));
                tv_button_about_show.setTextColor(ContextCompat.getColor(context, R.color.colorLight010));

                SELECTED_TAB = 1;
            } else if (item == 2) {
                layout_button_about_show.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_selected_bar_profile));
                tv_button_about_show.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
                layout_button_about_show.startAnimation(animationIn);

                grid_tab_profile.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_label_bar_profile));

                layout_button_work_show.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_no_selected_bar_profile));
                tv_button_work_show.setTextColor(ContextCompat.getColor(context, R.color.colorLight010));

                SELECTED_TAB = 2;
            }
        }
    }

    public void translateTextCard() {
        int visibility;
        if (!CONTENT_ON) {
            visibility = View.VISIBLE;
            arrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_dowm));
            CONTENT_ON = true;
        } else {
            visibility = View.GONE;
            arrow.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_dowm));
            CONTENT_ON = false;
        }
        TransitionManager.beginDelayedTransition(cv_button_show_info, new AutoTransition().setDuration(600));
        layout_information_show.setVisibility(visibility);
        flipAnimation();
    }

    private void flipAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(arrow, "rotation", angle, angle + 180);
        anim.setDuration(600);
        anim.start();
        angle += 180;
        angle = angle % 360;
    }

    private String randomStringBio() {
        ArrayList<String> list = new ArrayList<>();
        list.add(context.getString(R.string.text_complete_01));
        list.add(context.getString(R.string.text_complete_02));
        list.add(context.getString(R.string.text_complete_03));
        list.add(context.getString(R.string.text_complete_04));

        final int min = 0;
        final int max = 3;
        int random = new Random().nextInt((max - min) + 1) + min;

        return list.get(random);
    }

    public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine <= 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                } else if (tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                }
            }
        });

    }


}
