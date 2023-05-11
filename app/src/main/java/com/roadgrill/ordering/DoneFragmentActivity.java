package com.roadgrill.ordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.widget.EditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class DoneFragmentActivity extends  Fragment  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private double vsum = 0;
	private double length = 0;
	private double n = 0;
	
	private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear3;
	private RecyclerView recyclerview1;
	private EditText edittext1;
	
	private DatabaseReference history = _firebase.getReference("history");
	private ChildEventListener _history_child_listener;
	private Calendar now = Calendar.getInstance();
	private Intent i = new Intent();
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.done_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		com.google.firebase.FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		
		linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
		linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
		recyclerview1 = (RecyclerView) _view.findViewById(R.id.recyclerview1);
		edittext1 = (EditText) _view.findViewById(R.id.edittext1);
		
		edittext1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(getContext());
				
				View bottomSheetView; bottomSheetView = getLayoutInflater().inflate(R.layout.datepicker,null );
				bottomSheetDialog.setContentView(bottomSheetView);
				
				bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
				TextView t1 = (TextView) bottomSheetView.findViewById(R.id.t1);
				
				TextView t2 = (TextView) bottomSheetView.findViewById(R.id.t2);
				
				TextView b1 = (TextView) bottomSheetView.findViewById(R.id.b1);
				
				TextView b2 = (TextView) bottomSheetView.findViewById(R.id.b2);
				
				LinearLayout bg = (LinearLayout) bottomSheetView.findViewById(R.id.bg);
				
				LinearLayout np1 = (LinearLayout) bottomSheetView.findViewById(R.id.number_picker_one);
				
				LinearLayout np2 = (LinearLayout) bottomSheetView.findViewById(R.id.number_picker_two);
				
				LinearLayout np3 = (LinearLayout) bottomSheetView.findViewById(R.id.number_picker_tree);
				t1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
				t2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
				b1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
				b2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
				t1.setText("Select Dates");
				t2.setText("Select the date you want to see.");
				b1.setText("Dismiss");
				b2.setText("Search date");
				_rippleRoundStroke(bg, "#FFFFFF", "#000000", 15, 0, "#000000");
				_rippleRoundStroke(b1, "#FFFFFF", "#EEEEEE", 15, 2.5d, "#EEEEEE");
				_rippleRoundStroke(b2, "#F16622", "#FEFEFE", 15, 0, "#000000");
				final NumberPicker number_picker_1 = new NumberPicker(getContext());
				number_picker_1.setMaxValue(12);
				number_picker_1.setMinValue(0);
				number_picker_1.setWrapSelectorWheel(true);
				number_picker_1.setValue(1);
				np1.addView(number_picker_1);
				final NumberPicker number_picker_2 = new NumberPicker(getContext());
				number_picker_2.setMaxValue(31);
				number_picker_2.setMinValue(0);
				number_picker_2.setWrapSelectorWheel(true);
				number_picker_2.setValue(1);
				np2.addView(number_picker_2);
				final NumberPicker number_picker_3 = new NumberPicker(getContext());
				number_picker_3.setMaxValue(2030);
				number_picker_3.setMinValue(2019);
				number_picker_3.setWrapSelectorWheel(true);
				number_picker_3.setValue(2020);
				np3.addView(number_picker_3);
				b1.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
						bottomSheetDialog.dismiss();
					}
				});
				b2.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
						bottomSheetDialog.dismiss();
						edittext1.setText(String.valueOf((long)(number_picker_1.getValue())).concat("/".concat(String.valueOf((long)(number_picker_2.getValue())).concat("/".concat(String.valueOf((long)(number_picker_3.getValue())))))));
					}
				});
				bottomSheetDialog.setCancelable(true);
				bottomSheetDialog.show();
			}
		});
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				history.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						length = lmap.size();
						n = length - 1;
						for(int _repeat16 = 0; _repeat16 < (int)(length); _repeat16++) {
							if (lmap.get((int)n).get("timestamp").toString().toLowerCase().contains(_charSeq.toLowerCase())) {
								
							}
							else {
								lmap.remove((int)(n));
							}
							n--;
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
						recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		_history_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				history.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						SketchwareUtil.sortListMap(lmap, "pushkey", false, false);
						recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
						recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				history.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						SketchwareUtil.sortListMap(lmap, "pushkey", false, false);
						recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
						recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		history.addChildEventListener(_history_child_listener);
	}
	
	private void initializeLogic() {
		now = Calendar.getInstance();
		edittext1.setText(new SimpleDateFormat("MM/dd/yyyy").format(now.getTime()));
		_rippleRoundStroke(edittext1, "#FFFFFF", "#F16622", 15, 2.5d, "#F16622");
		SketchwareUtil.sortListMap(lmap, "pushkey", false, false);
		
		
		edittext1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
		_caps();
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _caps () {
		
	}
	
	
	public void _rippleRoundStroke (final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.done, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardview1 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview1);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear12 = (LinearLayout) _view.findViewById(R.id.linear12);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView date = (TextView) _view.findViewById(R.id.date);
			final LinearLayout linear13 = (LinearLayout) _view.findViewById(R.id.linear13);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final TextView username = (TextView) _view.findViewById(R.id.username);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView price = (TextView) _view.findViewById(R.id.price);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final LinearLayout linear14 = (LinearLayout) _view.findViewById(R.id.linear14);
			final LinearLayout linear15 = (LinearLayout) _view.findViewById(R.id.linear15);
			final TextView textview8 = (TextView) _view.findViewById(R.id.textview8);
			final TextView textview5 = (TextView) _view.findViewById(R.id.textview5);
			final TextView textview9 = (TextView) _view.findViewById(R.id.textview9);
			final TextView textview6 = (TextView) _view.findViewById(R.id.textview6);
			
			username.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
			
			textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
			price.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
			name.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
			textview4.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
			textview5.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
			textview6.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
			
			
			
			
			
			textview8.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
			textview9.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
			cardview1.setCardBackgroundColor(0xFFFEFEFE);
			cardview1.setRadius((float)20);
			cardview1.setCardElevation((float)5);
			cardview1.setPreventCornerOverlap(true);
			cardview1.setUseCompatPadding(true);
			if (lmap.get((int)_position).containsKey("productname")) {
				name.setText(lmap.get((int)_position).get("productname").toString());
			}
			if (lmap.get((int)_position).containsKey("price")) {
				price.setText(lmap.get((int)_position).get("price").toString());
			}
			if (lmap.get((int)_position).containsKey("product")) {
				Glide.with(getContext()).load(Uri.parse(lmap.get((int)_position).get("product").toString())).into(imageview1);
			}
			if (lmap.get((int)_position).containsKey("quantity")) {
				textview5.setText(lmap.get((int)_position).get("quantity").toString().concat("x"));
			}
			if (lmap.get((int)_position).containsKey("value")) {
				textview6.setText("₱".concat(lmap.get((int)_position).get("value").toString()));
			}
			if (lmap.get((int)_position).containsKey("table")) {
				username.setText("Table #".concat(lmap.get((int)_position).get("table").toString()));
			}
			if (lmap.get((int)_position).containsKey("timestamp")) {
				date.setText(lmap.get((int)_position).get("timestamp").toString());
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}
		
	}
	
	
}
