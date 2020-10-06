package turkusoweaplikacje.dmm.zaspiewane.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import turkusoweaplikacje.dmm.zaspiewane.R;

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.NumberViewHolder> {

    private int numberOfResultsToDisplay;
    private String[] lessonName;
    private String[] lessonPath;
    private String[] imagePath;
    private long[] lessonTime;

    final private ListItemClickListener onClickListener;

    public interface ListItemClickListener {
        void onListItemClick(String[] path, String[] image, String[] title, int episodeNumber);
    }

    public LessonListAdapter(int numberOfResultsToDisplay, ListItemClickListener listener){
        this.numberOfResultsToDisplay = numberOfResultsToDisplay;
        this.onClickListener = listener;
    }

    public void setLessonParameters(String[] names, String[] paths, String[] images, long[] time, int display){
        this.numberOfResultsToDisplay = display;
        this.lessonName = names;
        this.lessonPath = paths;
        this.imagePath = images;
        this.lessonTime = time;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.podcasts_list;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberOfResultsToDisplay;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView episodeName;
        private TextView episodeTime;
        private String buttonLink;

        public NumberViewHolder(View itemView) {
            super(itemView);
            episodeName = (TextView) itemView.findViewById(R.id.podcasts_tv_name);
            episodeTime = (TextView) itemView.findViewById(R.id.podcasts_tv_time);
            itemView.setOnClickListener(this);
        }

        /**
         * Displays number of translation result in order
         * @param itemIndex - translation result number
         */
        void bind(int itemIndex){
            episodeName.setText(lessonName[itemIndex]);
            episodeTime.setText(getTimeFromLong(lessonTime[itemIndex]));
            buttonLink = lessonPath[itemIndex];
        }

        @Override
        public void onClick(View view){
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(lessonPath, imagePath, lessonName,clickedPosition);
        }

        private String getTimeFromLong(long longTime){
            int minutes = (int) longTime/60000;
            int seconds = (int) (longTime%60000)/1000;
            return String.valueOf(minutes) + ":" + String.valueOf(seconds);
        }

    }

}
