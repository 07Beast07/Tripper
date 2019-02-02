package com.example.amuntimilsina.bideshisawari.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.amuntimilsina.bideshisawari.Helper.CardSliderLayoutManager;
import com.example.amuntimilsina.bideshisawari.Helper.CardSnapHelper;
import com.example.amuntimilsina.bideshisawari.Helper.DataParser;
import com.example.amuntimilsina.bideshisawari.Helper.DecodeBitmapTask;
import com.example.amuntimilsina.bideshisawari.Helper.DownloadUrl;
import com.example.amuntimilsina.bideshisawari.Helper.GetNearbyPlace;
import com.example.amuntimilsina.bideshisawari.Helper.SliderAdapter;
import com.example.amuntimilsina.bideshisawari.PlaceDetail;
import com.example.amuntimilsina.bideshisawari.R;
import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttractionFragment extends Fragment {
    //private final int[][] dotCoords = new int[5][2];
    private final int[] pics = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5};
    //private final int[] maps = {R.drawable.map_paris, R.drawable.map_seoul, R.drawable.map_london, R.drawable.map_beijing, R.drawable.map_greece};
    //private final int[] descriptions = {R.string.text1, R.string.text2, R.string.text3, R.string.text4, R.string.text5};
    //private final String[] countries = {"PARIS", "SEOUL", "LONDON", "BEIJING", "THIRA"};
    //private final String[] places = {"The Louvre", "Gwanghwamun", "Tower Bridge", "Temple of Heaven", "Aegeana Sea"};
    //private final String[] temperatures = {"21°C", "19°C", "17°C", "23°C", "20°C"};
    //private final String[] times = {"Aug 1 - Dec 15    7:00-18:00", "Sep 5 - Nov 10    8:00-16:00", "Mar 8 - May 21    7:00-18:00"};
    private ArrayList<String> rating = new ArrayList<>();
    private ArrayList<String> place = new ArrayList<>();
    private ArrayList<String> temperature = new ArrayList<>();
    private ArrayList<String> place_id = new ArrayList<>();
    private ArrayList<Double> lat = new ArrayList<>();
    private ArrayList<Double> lang = new ArrayList<>();
    private  SliderAdapter sliderAdapter;
    private CardSliderLayoutManager layoutManger;
    private RecyclerView recyclerView;
    private TextSwitcher temperatureSwitcher;
    private TextSwitcher ratingSwitcher;
    double mainlat=27.674436;
    double mainlang=85.365128;
    private TextView place1TextView;
    private TextView place2TextView;
    private int placeOffset1;
    private int placeOffset2;
    private long countryAnimDuration;
    private int currentPosition;

    private DecodeBitmapTask decodeMapBitmapTask;
    private DecodeBitmapTask.Listener mapLoadListener;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        initData();
        /*initRecyclerView(view);
        initCountryText(view);
        initSwitchers(view);*/
        return view;
    }
    public void datafinalize(ArrayList<String> rating,ArrayList<String> place_id,ArrayList<String> place,ArrayList<String> temperature,ArrayList<Double> lat,ArrayList<Double> lang){
        this.rating=rating;
        this.place=place;
        this.place_id=place_id;
        this.temperature=temperature;
        this.lat=lat;
        this.lang=lang;
        sliderAdapter = new SliderAdapter(pics, place.size(), new AttractionFragment.OnCardClickListener());
        Log.i("11datafinalizaton",""+place.size());
        if(place.size()>0) {
            initRecyclerView(getView());
            initCountryText(getView());
            initSwitchers(getView());
        }
    }
    //Data initialization of places and its info
    private void initData() {
        String url=getUrl(mainlat,mainlang);
        Object datat[]=new Object[1];
        datat[0]=url;
        Log.i("one",""+datat[0]);
        GetNearbyPlace getNearbyPlace=new GetNearbyPlace();
        getNearbyPlace.execute(datat);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(sliderAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        layoutManger = (CardSliderLayoutManager) recyclerView.getLayoutManager();

        new CardSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity().isFinishing() && decodeMapBitmapTask != null) {
            decodeMapBitmapTask.cancel(true);
        }
    }

    private void initSwitchers(View view) {
      /*  temperatureSwitcher = (TextSwitcher) view.findViewById(R.id.ts_temperature);
        temperatureSwitcher.setFactory(new AttractionFragment.TextViewFactory(R.style.TemperatureTextView, true));
        temperatureSwitcher.setCurrentText(temperature.get(0));
*/
        ratingSwitcher = (TextSwitcher) view.findViewById(R.id.ts_rating);
        ratingSwitcher.setFactory(new AttractionFragment.TextViewFactory(R.style.PlaceTextView, false));
        ratingSwitcher.setCurrentText(rating.get(0));

        //clockSwitcher = (TextSwitcher) view.findViewById(R.id.ts_clock);
        //clockSwitcher.setFactory(new AttractionFragment.TextViewFactory(R.style.ClockTextView, false));
        //clockSwitcher.setCurrentText(times[0]);

       /* descriptionsSwitcher = (TextSwitcher) view.findViewById(R.id.ts_description);
        descriptionsSwitcher.setInAnimation(getActivity(), android.R.anim.fade_in);
        descriptionsSwitcher.setOutAnimation(getActivity(), android.R.anim.fade_out);
        descriptionsSwitcher.setFactory(new AttractionFragment.TextViewFactory(R.style.DescriptionTextView, false));
        descriptionsSwitcher.setCurrentText(getString(descriptions[0]));*/

      /*  mapSwitcher = (ImageSwitcher) view.findViewById(R.id.ts_map);
        mapSwitcher.setInAnimation(getActivity(), R.anim.fade_in);
        mapSwitcher.setOutAnimation(getActivity(), R.anim.fade_out);
        mapSwitcher.setFactory(new AttractionFragment.ImageViewFactory());
        mapSwitcher.setImageResource(maps[0]);*/
/*
        mapLoadListener = new DecodeBitmapTask.Listener() {
            @Override
            public void onPostExecuted(Bitmap bitmap) {
                ((ImageView)mapSwitcher.getNextView()).setImageBitmap(bitmap);
                mapSwitcher.showNext();
            }
        };*/
    }

    private void initCountryText(View view) {
        countryAnimDuration = getResources().getInteger(R.integer.labels_animation_duration);
        placeOffset1 = getResources().getDimensionPixelSize(R.dimen.left_offset);
        placeOffset2 = getResources().getDimensionPixelSize(R.dimen.card_width);
        place1TextView = (TextView) view.findViewById(R.id.tv_country_1);
        place2TextView = (TextView) view.findViewById(R.id.tv_country_2);

        place1TextView.setX(placeOffset1);
        place2TextView.setX(placeOffset2);
        place1TextView.setText(place.get(0));
        place2TextView.setAlpha(0f);

        place1TextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "open-sans-extrabold.ttf"));
        place2TextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "open-sans-extrabold.ttf"));
    }

   /* private void initGreenDot(final View view) {
        mapSwitcher.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                mapSwitcher.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener) getParentFragment());

                final int viewLeft = mapSwitcher.getLeft();
                final int viewTop = mapSwitcher.getTop() + mapSwitcher.getHeight() / 3;

                final int border = 100;
                final int xRange = Math.max(1, mapSwitcher.getWidth() - border * 2);
                final int yRange = Math.max(1, (mapSwitcher.getHeight() / 3) * 2 - border * 2);

                final Random rnd = new Random();

                for (int i = 0, cnt = dotCoords.length; i < cnt; i++) {
                    dotCoords[i][0] = viewLeft + border + rnd.nextInt(xRange);
                    dotCoords[i][1] = viewTop + border + rnd.nextInt(yRange);
                }

                greenDot = view.findViewById(R.id.green_dot);
                greenDot.setX(dotCoords[0][0]);
                greenDot.setY(dotCoords[0][1]);
            }
        });
    }*/

    private void setCountryText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (place1TextView.getAlpha() > place2TextView.getAlpha()) {
            visibleText = place1TextView;
            invisibleText = place2TextView;
        } else {
            visibleText = place2TextView;
            invisibleText = place1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = placeOffset2;
        } else {
            invisibleText.setX(placeOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", placeOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(countryAnimDuration);
        animSet.start();
    }

    private void onActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }
        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {
        int animH[] = new int[]{R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[]{R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }

        setCountryText(place.get(pos % place.size()), left2right);

        /*temperatureSwitcher.setInAnimation(getActivity(), animH[0]);
        temperatureSwitcher.setOutAnimation(getActivity(), animH[1]);
        temperatureSwitcher.setText(temperature.get(pos % temperature.size()));*/

        ratingSwitcher.setInAnimation(getActivity(), animV[0]);
        ratingSwitcher.setOutAnimation(getActivity(), animV[1]);
        ratingSwitcher.setText(rating.get(pos % rating.size()));

       /* clockSwitcher.setInAnimation(getActivity(), animV[0]);
        clockSwitcher.setOutAnimation(getActivity(), animV[1]);
        clockSwitcher.setText(times[pos % times.length]);

        descriptionsSwitcher.setText(getString(descriptions[pos % descriptions.length]));

        showMap(maps[pos % maps.length]);

        ViewCompat.animate(greenDot)
                .translationX(dotCoords[pos % dotCoords.length][0])
                .translationY(dotCoords[pos % dotCoords.length][1])
                .start();
*/
        currentPosition = pos;
    }

   /* private void showMap(@DrawableRes int resId) {
        if (decodeMapBitmapTask != null) {
            decodeMapBitmapTask.cancel(true);
        }

        final int w = mapSwitcher.getWidth();
        final int h = mapSwitcher.getHeight();

        decodeMapBitmapTask = new DecodeBitmapTask(getResources(), resId, w, h, mapLoadListener);
        decodeMapBitmapTask.execute();
    }*/

    private class TextViewFactory implements ViewSwitcher.ViewFactory {

        @StyleRes
        int styleId;
        boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(getActivity());

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(getActivity(), styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }

    }

    private class ImageViewFactory implements ViewSwitcher.ViewFactory {
        @Override
        public View makeView() {
            final ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            final ViewGroup.LayoutParams lp = new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);

            return imageView;
        }
    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // startActivity(new Intent(getActivity(),TransportFragment.class));
            final CardSliderLayoutManager lm = (CardSliderLayoutManager) recyclerView.getLayoutManager();
            final int activeCardPosition = lm.getActiveCardPosition();
            final int clickedPosition = recyclerView.getChildAdapterPosition(view);
            if (lm.isSmoothScrolling()) {
                return;
            }

            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }
            if (clickedPosition > activeCardPosition) {
                recyclerView.smoothScrollToPosition(clickedPosition);
                onActiveCardChange(clickedPosition);
            }

            if (clickedPosition == activeCardPosition) {
                final Intent intent = new Intent(getActivity(), PlaceDetail.class);
                intent.putExtra("position", place.get(clickedPosition % place.size()));
                startActivity(intent);

            }

        }
    }

    public class GetNearbyPlace extends AsyncTask<Object,String,String> {

        String googlePlacesData;
        GoogleMap googleMap;
        String url;
        @Override
        protected String doInBackground(Object... objects) {
          //  googleMap= (GoogleMap) objects[0];
            url= (String) objects[0];
            DownloadUrl downloadUrl=new DownloadUrl();
            try {
                googlePlacesData=downloadUrl.readUrl(url);
                Log.i("passcccheck",""+googlePlacesData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return googlePlacesData;
        }

        @Override
        protected void onPostExecute(String s) {
            List<HashMap<String, String>> nearbyPlaceList;
            DataParser parser = new DataParser();
            nearbyPlaceList = parser.parse(s);
            Log.i("cccheck",""+nearbyPlaceList);

            showNearbyPlaces(nearbyPlaceList);
        }
        private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
        {
            for(int i = 0; i < nearbyPlaceList.size(); i++)
            {
                HashMap<String, String> googlePlace = nearbyPlaceList.get(i);
                 place.add(googlePlace.get("Place_Name"));
                 lat.add(Double.parseDouble( googlePlace.get("lat")));
                 lang.add(Double.parseDouble( googlePlace.get("lang")));
                 temperature.add(googlePlace.get("Vicinity"));
                 rating.add(googlePlace.get("rating"));
                 temperature.add(googlePlace.get("Vicinity"));
                place_id.add(googlePlace.get("place_id"));
                 Log.i("finaldata",""+place);
                //String vicinity = googlePlace.get("vicinity");
                /*double lat = Double.parseDouble( googlePlace.get("lat"));
                double lng = Double.parseDouble( googlePlace.get("lng"));*/
            }
            datafinalize(rating,place_id,place,temperature,lat,lang);
        }
    }
    public String getUrl(double lat,double lang)
    {
        StringBuilder googlePlaceUrl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+lat+","+lang);
        googlePlaceUrl.append("&type=restaurant&radius=1000&key="+"AIzaSyAcSkMHAEdafLQT7LvTlJflrDwLfMdu6sU");
        Log.i("dataaaa",googlePlaceUrl.toString());
        return googlePlaceUrl.toString();

    }
}
