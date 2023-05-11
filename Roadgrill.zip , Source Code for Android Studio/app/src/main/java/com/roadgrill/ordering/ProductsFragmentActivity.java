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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.ClipData;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Continuation;
import java.io.File;
import android.app.Activity;
import android.content.SharedPreferences;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.View;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class ProductsFragmentActivity extends  Fragment  { 
	
	public final int REQ_CD_F = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	private String avatar = "";
	private String name = "";
	private String path = "";
	private HashMap<String, Object> mapp = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();
	private ArrayList<String> ck = new ArrayList<>();
	
	private LinearLayout linear4;
	private TextView textview1;
	private RecyclerView recyclerview1;
	
	private Intent i = new Intent();
	private DatabaseReference products = _firebase.getReference("producs");
	private ChildEventListener _products_child_listener;
	private Intent f = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference fstore = _firebase_storage.getReference("fstore");
	private OnCompleteListener<Uri> _fstore_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fstore_download_success_listener;
	private OnSuccessListener _fstore_delete_success_listener;
	private OnProgressListener _fstore_upload_progress_listener;
	private OnProgressListener _fstore_download_progress_listener;
	private OnFailureListener _fstore_failure_listener;
	private SharedPreferences sp;
	private ObjectAnimator oa_d = new ObjectAnimator();
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.products_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		com.google.firebase.FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		
		linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
		textview1 = (TextView) _view.findViewById(R.id.textview1);
		recyclerview1 = (RecyclerView) _view.findViewById(R.id.recyclerview1);
		f.setType("image/*");
		f.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		sp = getContext().getSharedPreferences("sp", Activity.MODE_PRIVATE);
		
		textview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_dialog();
			}
		});
		
		_products_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				products.addListenerForSingleValueEvent(new ValueEventListener() {
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
						ck.add(_childKey);
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
				products.addListenerForSingleValueEvent(new ValueEventListener() {
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
				products.addListenerForSingleValueEvent(new ValueEventListener() {
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
						recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
						recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		products.addChildEventListener(_products_child_listener);
		
		_fstore_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fstore_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fstore_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				mapp = new HashMap<>();
				mapp.put("name", sp.getString("name", ""));
				mapp.put("price", sp.getString("price", ""));
				mapp.put("image", _downloadUrl);
				products.push().updateChildren(mapp);
				mapp.clear();
				sp.edit().remove("name").commit();
				sp.edit().remove("price").commit();
				_aProGress(false);
			}
		};
		
		_fstore_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_fstore_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_fstore_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
	}
	
	private void initializeLogic() {
		_rippleRoundStroke(textview1, "#FFFFFF", "#F16622", 15, 2.5d, "#F16622");
		textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_F:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getContext(), _data.getData()));
					}
				}
				path = _filePath.get((int)(0));
				fstore.child(Uri.parse(path).getLastPathSegment()).putFile(Uri.fromFile(new File(path))).addOnFailureListener(_fstore_failure_listener).addOnProgressListener(_fstore_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return fstore.child(Uri.parse(path).getLastPathSegment()).getDownloadUrl();
					}}).addOnCompleteListener(_fstore_upload_success_listener);
				_aProGress(true);
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	public void _shape (final double _top1, final double _top2, final double _bottom2, final double _bottom1, final String _inside_color, final String _side_color, final double _side_size, final View _view) {
		Double tlr = _top1;
		Double trr = _top2;
		Double blr = _bottom2;
		Double brr = _bottom1;
		Double sw = _side_size;
		android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
		s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		s.setCornerRadii(new float[] {tlr.floatValue(),tlr.floatValue(), trr.floatValue(),trr.floatValue(), blr.floatValue(),blr.floatValue(), brr.floatValue(),brr.floatValue()}); 
		
		s.setColor(Color.parseColor(_inside_color));
		s.setStroke(sw.intValue(), Color.parseColor(_side_color));
		_view.setBackground(s);
	}
	
	
	public void _dialog () {
		final AlertDialog dialog2 = new AlertDialog.Builder(getContext()).create();
		View inflate = getLayoutInflater().inflate(R.layout.addproduct, null);
		dialog2.setView(inflate);
		dialog2.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
		dialog2.setCanceledOnTouchOutside(true);
		dialog2.setCancelable(false);
		final LinearLayout lin2 = (LinearLayout) inflate.findViewById(R.id.linear2);
		final LinearLayout lin1 = (LinearLayout) inflate.findViewById(R.id.linear1);
		final LinearLayout lin3 = (LinearLayout) inflate.findViewById(R.id.linear3);
		final LinearLayout lin4 = (LinearLayout) inflate.findViewById(R.id.linear4);
		final LinearLayout lin5 = (LinearLayout) inflate.findViewById(R.id.linear5);
		final LinearLayout lin6 = (LinearLayout) inflate.findViewById(R.id.linear6);
		final LinearLayout lin7 = (LinearLayout) inflate.findViewById(R.id.linear7);
		final TextView txt2 = (TextView) inflate.findViewById(R.id.textview2);
		final EditText name = (EditText) inflate.findViewById(R.id.name);
		final EditText price = (EditText) inflate.findViewById(R.id.price);
		final ImageView img1 = (ImageView) inflate.findViewById(R.id.imageview1);
		final LinearLayout lin8 = (LinearLayout) inflate.findViewById(R.id.linear8);
		final LinearLayout lin9 = (LinearLayout) inflate.findViewById(R.id.linear9);
		final TextView txt1 = (TextView) inflate.findViewById(R.id.textview1);
		lin1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFFEFEFE));
		lin2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFFEFEFE));
		lin8.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFFFFFFF));
		lin9.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFFFFFFF));
		_shape(10, 10, 0, 0, "#F16622", "#FFFFFF", 0, lin7);
		lin8.setElevation((float)5);
		lin9.setElevation((float)5);
		name.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
		price.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
		txt1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
		txt2.setOnClickListener(new OnClickListener() { public void onClick(View view) {
				if ((name.getText().toString().length() > 0) && (price.getText().toString().length() > 0)) {
					startActivityForResult(f, REQ_CD_F);
					sp.edit().putString("name", name.getText().toString()).commit();
					sp.edit().putString("price", price.getText().toString()).commit();
				}
				else {
					SketchwareUtil.showMessage(getContext(), "Field is empty");
				}
				dialog2.dismiss(); } });
		dialog2.show();
	}
	
	
	public void _bounce (final View _view) {
		oa_d.setTarget(_view);
		oa_d.setPropertyName("rotation");
		oa_d.setFloatValues((float)(90), (float)(0));
		oa_d.setDuration((int)(1000));
		oa_d.setInterpolator(new BounceInterpolator());
		oa_d.start();
	}
	
	
	public void _setBgCorners (final View _view, final double _radius, final String _color) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(); 
		gd.setColor(Color.parseColor("#" + _color.replace("#", ""))); /* color */
		gd.setCornerRadius((int)_radius); /* radius */
		gd.setStroke(0, Color.WHITE); /* stroke heigth and color */
		_view.setBackground(gd);
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
	
	
	public void _aProGress (final boolean _ifShow) {
		if (_ifShow) {
			if (prog == null){
				prog = new ProgressDialog(getContext());
				prog.setCancelable(false);
				prog.setCanceledOnTouchOutside(false);
				
				prog.requestWindowFeature(Window.FEATURE_NO_TITLE);  prog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
				
			}
			prog.setMessage(null);
			prog.show();
			prog.setContentView(R.layout.cus);
		}
		else {
			if (prog != null){
				prog.dismiss();
			}
		}
	}
	private ProgressDialog prog;
	{
	}
	
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.allproducts, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardview1 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview1);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView price = (TextView) _view.findViewById(R.id.price);
			final Button button2 = (Button) _view.findViewById(R.id.button2);
			
			button2.setElevation((float)5);
			imageview1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, Color.TRANSPARENT));
			name.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
			price.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
			
			button2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ssb.ttf"), 0);
			textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/sss.ttf"), 0);
			cardview1.setCardBackgroundColor(0xFFFEFEFE);
			cardview1.setRadius((float)20);
			cardview1.setCardElevation((float)5);
			cardview1.setPreventCornerOverlap(true);
			cardview1.setUseCompatPadding(true);
			button2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFF16622));
			if (lmap.get((int)_position).containsKey("name")) {
				name.setText(lmap.get((int)_position).get("name").toString());
			}
			if (lmap.get((int)_position).containsKey("price")) {
				price.setText(lmap.get((int)_position).get("price").toString());
			}
			if (lmap.get((int)_position).containsKey("image")) {
				Glide.with(getContext()).load(Uri.parse(lmap.get((int)_position).get("image").toString())).into(imageview1);
			}
			button2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					final AlertDialog info_dialog = new AlertDialog.Builder(getContext()).create();
					LayoutInflater inflater = getLayoutInflater();
					
					View convertView = (View) inflater.inflate(R.layout.diainfo, null);
					info_dialog.setView(convertView);
					
					info_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  info_dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
					
					
					LinearLayout i_bg = (LinearLayout) convertView.findViewById(R.id.linear_bg);
					
					LinearLayout i_div = (LinearLayout) convertView.findViewById(R.id.linear_div);
					
					Button i_ok = (Button) convertView.findViewById(R.id.okay_button);
					
					Button i_cancel = (Button) convertView.findViewById(R.id.cancel_button);
					
					ImageView i_header = (ImageView) convertView.findViewById(R.id.img_header);
					
					TextView i_title = (TextView) convertView.findViewById(R.id.txt_title);
					
					TextView i_msg = (TextView) convertView.findViewById(R.id.txt_msg);
					
					_setBgCorners(i_bg, 8, "#FFFFFF");
					_setBgCorners(i_ok, 8, "#F16622");
					_setBgCorners(i_cancel, 8, "#2D2D39");
					i_header.setElevation(5);
					i_ok.setOnClickListener(new View.OnClickListener(){
						    public void onClick(View v){
							products.child(ck.get((int)(_position))).removeValue();
							FancyToast.makeText(getContext(), "Success", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
							
							info_dialog.dismiss();
						}});
					i_cancel.setOnClickListener(new View.OnClickListener(){
						    public void onClick(View v){
							info_dialog.dismiss();
						}});
					info_dialog.show();
					_bounce(i_header);
				}
			});
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
