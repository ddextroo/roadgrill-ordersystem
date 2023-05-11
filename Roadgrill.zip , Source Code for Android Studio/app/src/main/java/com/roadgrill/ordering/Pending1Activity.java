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
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import org.json.JSONObject;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.view.View;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class Pending1Activity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String pend = "";
	private double vsum = 0;
	private String w1 = "";
	private String w2 = "";
	private String w3 = "";
	private String w4 = "";
	private String historymen = "";
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();
	private ArrayList<String> childkey = new ArrayList<>();
	private ArrayList<String> ck = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private RecyclerView recyclerview1;
	private TextView textview1;
	private TextView textview2;
	
	private Intent i = new Intent();
	private DatabaseReference pending = _firebase.getReference(""+pend+"");
	private ChildEventListener _pending_child_listener;
	private FirebaseAuth auth;
	private OnCompleteListener<Void> auth_updateEmailListener;
	private OnCompleteListener<Void> auth_updatePasswordListener;
	private OnCompleteListener<Void> auth_emailVerificationSentListener;
	private OnCompleteListener<Void> auth_deleteUserListener;
	private OnCompleteListener<Void> auth_updateProfileListener;
	private OnCompleteListener<AuthResult> auth_phoneAuthListener;
	private OnCompleteListener<AuthResult> auth_googleSignInListener;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	
	private OnCompleteListener c_onCompleteListener;
	private OSPermissionSubscriptionState os;
	private DatabaseReference history = _firebase.getReference("history");
	private ChildEventListener _history_child_listener;
	private Calendar cal = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.pending1);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		auth = FirebaseAuth.getInstance();
		
		textview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_pending_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				pending.addListenerForSingleValueEvent(new ValueEventListener() {
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
						recyclerview1.setLayoutManager(new LinearLayoutManager(Pending1Activity.this));
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
				pending.addListenerForSingleValueEvent(new ValueEventListener() {
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
						recyclerview1.setLayoutManager(new LinearLayoutManager(Pending1Activity.this));
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
				pending.addListenerForSingleValueEvent(new ValueEventListener() {
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
						recyclerview1.setLayoutManager(new LinearLayoutManager(Pending1Activity.this));
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
		pending.addChildEventListener(_pending_child_listener);
		
		_history_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
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
		
		auth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		auth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		linear2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c) { this.setStroke(a, b); this.setColor(c); return this; } }.getIns((int)1, 0xFFBDBDBD, 0xFFFFFFFF));
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ssb.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/sss.ttf"), 0);
		pending.removeEventListener(_pending_child_listener);
		pend = "pending/".concat(getIntent().getStringExtra("uid").concat("/"));
		pending =_firebase.getReference(pend);
		pending.addChildEventListener(_pending_child_listener);
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			
			os = OneSignal.getPermissionSubscriptionState();
			OneSignal.init(Pending1Activity.this, "160238266133", "040d98b8-7680-4728-9b09-665ad8df0cb3");
			 OneSignal.getCurrentOrNewInitBuilder()
			.inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
			.unsubscribeWhenNotificationsAreDisabled(true).init();
			OSPermissionSubscriptionState os = OneSignal.getPermissionSubscriptionState();
			
			boolean isEnabled = os.getPermissionStatus().getEnabled();
			boolean isSubscribed = os.getSubscriptionStatus().getSubscribed();
			boolean subscriptionSetting = os.getSubscriptionStatus().getUserSubscriptionSetting();
			String userID = os.getSubscriptionStatus().getUserId();
			String pushToken = os.getSubscriptionStatus().getPushToken();
			
			OneSignal.setSubscription(true);
		}
		pending.addListenerForSingleValueEvent(new ValueEventListener() {
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
				for (DataSnapshot dsv : _dataSnapshot.getChildren()) {
					
					Map<String,Object> mapv = (Map<String,Object>)
					dsv.getValue();
					Object value = mapv.get("value");
					int v_value = Integer.parseInt(String.valueOf(value));
					vsum += v_value;
				}
				textview2.setText("₱".concat(String.valueOf((long)(vsum))));
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.userpending, null);
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
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final LinearLayout linear13 = (LinearLayout) _view.findViewById(R.id.linear13);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final TextView date = (TextView) _view.findViewById(R.id.date);
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
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final Button button1 = (Button) _view.findViewById(R.id.button1);
			final Button button2 = (Button) _view.findViewById(R.id.button2);
			
			username.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ssb.ttf"), 0);
			
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ssb.ttf"), 0);
			price.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/sss.ttf"), 0);
			name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/sss.ttf"), 0);
			textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/sss.ttf"), 0);
			textview5.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/sss.ttf"), 0);
			textview6.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/sss.ttf"), 0);
			
			button1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ssb.ttf"), 0);
			button2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ssb.ttf"), 0);
			
			
			textview8.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ssb.ttf"), 0);
			textview9.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ssb.ttf"), 0);
			linear5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c) { this.setStroke(a, b); this.setColor(c); return this; } }.getIns((int)1, 0xFFBDBDBD, 0xFFFEFEFE));
			button1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFF16622));
			button2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFF16622));
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
				Glide.with(getApplicationContext()).load(Uri.parse(lmap.get((int)_position).get("product").toString())).into(imageview1);
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
			OSPermissionSubscriptionState os = OneSignal.getPermissionSubscriptionState();
			
			boolean isEnabled = os.getPermissionStatus().getEnabled();
			boolean isSubscribed = os.getSubscriptionStatus().getSubscribed();
			boolean subscriptionSetting = os.getSubscriptionStatus().getUserSubscriptionSetting();
			String userID = os.getSubscriptionStatus().getUserId();
			String pushToken = os.getSubscriptionStatus().getPushToken();
			
			button1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					pending.child(ck.get((int)(_position))).removeValue();
					w1 = "Your order (".concat(lmap.get((int)_position).get("productname").toString().concat(" - ".concat(lmap.get((int)_position).get("quantity").toString().concat("x"))).concat(") has been cancelled."));
					w2 = lmap.get((int)_position).get("notifid").toString();
					w3 = "Roadgrill";
					w4 = "null";
					if (!true)
					return;
					
					try {
						   JSONObject notificationContent = new JSONObject("{'contents': {'en': '" + w1 + "'}," +
						           "'include_player_ids': ['" + w2 + "'], " +
						           "'headings': {'en': '" + w3 + "'}, " +
						           "'big_picture': '" + w4 + "'}");
						   OneSignal.postNotification(notificationContent, null);
					} catch (org.json.JSONException e) {
						   e.printStackTrace();
					}
					SketchwareUtil.showMessage(getApplicationContext(), "Order deleted");
				}
			});
			button2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					cal = Calendar.getInstance();
					pending.child(ck.get((int)(_position))).removeValue();
					w1 = "Your order (".concat(lmap.get((int)_position).get("productname").toString().concat(" - ".concat(lmap.get((int)_position).get("quantity").toString().concat("x"))).concat(") is now ready."));
					w2 = lmap.get((int)_position).get("notifid").toString();
					w3 = "Roadgrill";
					w4 = "null";
					map = new HashMap<>();
					map.put("table", lmap.get((int)_position).get("table").toString());
					map.put("notifid", lmap.get((int)_position).get("notifid").toString());
					map.put("productname", lmap.get((int)_position).get("productname").toString());
					map.put("price", lmap.get((int)_position).get("price").toString());
					map.put("product", lmap.get((int)_position).get("product").toString());
					map.put("quantity", lmap.get((int)_position).get("quantity").toString());
					map.put("total", lmap.get((int)_position).get("total").toString());
					map.put("value", lmap.get((int)_position).get("value").toString());
					map.put("pushkey", history.push().getKey());
					map.put("uid", getIntent().getStringExtra("uid"));
					map.put("timestamp", new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime()));
					history.push().updateChildren(map);
					map.clear();
					if (!true)
					return;
					
					try {
						   JSONObject notificationContent = new JSONObject("{'contents': {'en': '" + w1 + "'}," +
						           "'include_player_ids': ['" + w2 + "'], " +
						           "'headings': {'en': '" + w3 + "'}, " +
						           "'big_picture': '" + w4 + "'}");
						   OneSignal.postNotification(notificationContent, null);
					} catch (org.json.JSONException e) {
						   e.printStackTrace();
					}
					SketchwareUtil.showMessage(getApplicationContext(), "Done");
					finish();
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
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
