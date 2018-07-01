package io.exception.journalapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by C. Kasonde on 7/1/2018.
 */

public class EntriesListAdapter extends RecyclerView.Adapter<EntriesListAdapter.ViewHolder> {
    public List<Entries> entriesList;
    public Context mContext;
    String title;
    String entry;
    String date;

    public EntriesListAdapter(Context context, List<Entries> entriesList) {
        mContext = context;
        this.entriesList = entriesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.titleTextView.setText(entriesList.get(position).getTitle());
        holder.entryTextView.setText(entriesList.get(position).getEntry());
        holder.dateTextView.setText(entriesList.get(position).getDate());

       final String entry_id = entriesList.get(position).entryId;

        title = entriesList.get(position).getTitle();

        entry = entriesList.get(position).getEntry();

        date = entriesList.get(position).getDate();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                transferDocument(entry_id, title, entry, date);

            }
        });
    }

    private void transferDocument(String entry_id, String title, String entry, String date) {
        Intent intentMove = new Intent(mContext, DetailView.class);
        intentMove.putExtra("document_id", entry_id);
        intentMove.putExtra("document_title", title);
        intentMove.putExtra("document_entry", entry);
        intentMove.putExtra("document_date", date);
        mContext.startActivity(intentMove);

    }


    @Override
    public int getItemCount() {
        return entriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public TextView titleTextView, entryTextView, dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            titleTextView =(TextView)mView.findViewById(R.id.txTitle);
            entryTextView = (TextView)mView.findViewById(R.id.txEntry);
            dateTextView = (TextView)mView.findViewById(R.id.txDate);
        }
    }
}
