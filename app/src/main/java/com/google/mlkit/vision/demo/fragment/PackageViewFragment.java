package com.google.mlkit.vision.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.adapter.PackageAdapter;
import com.google.mlkit.vision.demo.event.BarcodeEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.*;

public class PackageViewFragment extends Fragment {
    private static final String TAG = "PackageViewFragment";

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }


    protected RecyclerView mRecyclerView;
    protected PackageAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<PackageLinkData> mDataset;
    protected Set<String> mScannedOriginalBarcodes;
    protected Set<String> mScannedTargetBarcodes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.package_view_frag, container, false);
        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);

        mAdapter = new PackageAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    // This method will be called when a Barcode is detected
    @Subscribe
    public void onDetectedBarcode(BarcodeEvent barcodeEvent) {
        Toast.makeText(getActivity(), "Tôi vừa nhận được mã barcode là: " + barcodeEvent.message, Toast.LENGTH_SHORT).show();

        if (barcodeEvent.message.length() == 6 && !mScannedTargetBarcodes.contains(barcodeEvent.message)) {
            mScannedTargetBarcodes.add(barcodeEvent.message);
        } else if (!mScannedOriginalBarcodes.contains(barcodeEvent.message)) {
            mScannedOriginalBarcodes.add(barcodeEvent.message);
        }



        mAdapter.notifyDataSetChanged();

    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        mDataset = new ArrayList<>();
        mScannedOriginalBarcodes = new HashSet<>();
        mScannedTargetBarcodes = new HashSet<>();

    }
}
