package sg.edu.np.mad.snatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<ScreenItem> mItemList;

    public IntroViewPagerAdapter(Context mContext, List<ScreenItem> mItemList){
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen, null);
        ImageView imgSlide = layoutScreen.findViewById(R.id.introImage);
        TextView title = layoutScreen.findViewById(R.id.title);
        TextView description = layoutScreen.findViewById(R.id.description);

        title.setText(mItemList.get(position).getTitle());
        description.setText(mItemList.get(position).getDescription());
        imgSlide.setImageResource(mItemList.get(position).getScreenImg());

        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
