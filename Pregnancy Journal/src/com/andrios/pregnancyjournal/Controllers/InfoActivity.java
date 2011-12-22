package com.andrios.pregnancyjournal.Controllers;


import java.util.ArrayList;

import com.andrios.pregnancyjournal.R;
import com.andrios.pregnancyjournal.Models.Profile;
import com.andrios.pregnancyjournal.Models.Week;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class InfoActivity extends Activity implements OnGesturePerformedListener {
	

    
	ViewFlipper flipper;
	TextView previousLBL, currentLBL, nextLBL;
	Profile profile;
	int currentWeek;

	GestureLibrary mLibrary;
	
	ArrayList<Week> weekList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.infoview);
        
     
        
        
    }
    
    public void onResume(){
    	super.onResume();
    	getExtras();
        setConnections();
        setOnClickListeners();
        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!mLibrary.load()) {
            finish();
        }
    }
    
	@SuppressWarnings("unchecked")
	private void getExtras() {
		Intent intent = this.getIntent();
		profile = (Profile) intent.getSerializableExtra("profile");
		
		currentWeek = profile.getWeek() - 1;
		if(currentWeek < 0){
			currentWeek = 0;
		}
		
		buildWeeks();
	}
	
	private void setConnections() {
		
		previousLBL = (TextView) findViewById(R.id.infoViewPreviousLBL);
		
		currentLBL = (TextView) findViewById(R.id.infoViewCurrentLBL);
		
		
		nextLBL = (TextView) findViewById(R.id.infoViewNextLBL);
		
		
		
		flipper = (ViewFlipper) findViewById(R.id.details); 
		setNextWeekView(flipper.getChildAt(flipper.getDisplayedChild()));
		flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
		
		
	    GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.infoActivityGestureOverlay);
	 	  gestures.addOnGesturePerformedListener(this);
		
	
	}
	
	private void setOnClickListeners(){
		previousLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				previous();
				
				
			}

			
			
		});
		
		nextLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				next();
				
				
			}

			
			
		});
		
	}
	
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
	    ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
	    System.out.println("GESTURE");
	    // We want at least one prediction
	    if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
	        String action = predictions.get(0).name;
	        System.out.println("Action Name: " + action);
	        if ("left".equals(action)) {
	        	next();
	        } else if ("right".equals(action)) {
	        	previous();
	        }
	    }
	}
	
	private void previous() {
		flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
		
		View v = null;
		if(currentWeek >= 2){
			if(flipper.getChildAt(flipper.getDisplayedChild() + 1) != null){
				v = flipper.getChildAt(flipper.getDisplayedChild() + 1);
				
			}else{
				v = flipper.getChildAt(flipper.getDisplayedChild() - 1);
			}
			currentWeek -=1;
			setNextWeekView(v);
			flipper.showNext();
		}
		
	}
	
	
	private void next(){
		flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
		
		View v = null;
		if(currentWeek <= 38){
			if(flipper.getChildAt(flipper.getDisplayedChild() + 1) != null){
				v = flipper.getChildAt(flipper.getDisplayedChild() + 1);
				
			}else{
				v = flipper.getChildAt(flipper.getDisplayedChild() - 1);
			}
			currentWeek += 1;
			setNextWeekView(v);
			flipper.showNext();
		}
	}
	
	private void setNextWeekView(View v){
		
		if(currentWeek >= 2){
			previousLBL.setText(weekList.get(currentWeek -1).getName());
		}else{
			previousLBL.setText("      ");
		}
		
		currentLBL.setText(weekList.get(currentWeek).getName());
		
		if(currentWeek <= 38){
			nextLBL.setText(weekList.get(currentWeek +1).getName());
		}else{
			nextLBL.setText("      ");
		}
		
		TextView size = (TextView) v.findViewById(R.id.infoViewSizeLBL);
		size.setText(weekList.get(currentWeek).getSize());
		
		TextView length = (TextView) v.findViewById(R.id.infoViewLengthLBL);
		length.setText(weekList.get(currentWeek).getLength());
		
		TextView weight = (TextView) v.findViewById(R.id.infoViewWeightLBL);
		weight.setText(weekList.get(currentWeek).getWeight());
		
		TextView description = (TextView) v.findViewById(R.id.infoViewNotesTXT);
		description.setText(weekList.get(currentWeek).getDescription() + "\n\n" + weekList.get(currentWeek).getWhatToDo());
		
		
		ImageView image = (ImageView) v.findViewById(R.id.infoViewImageView);
		image.setImageResource(weekList.get(currentWeek).getImage());
	}
	
	
	private void buildWeeks(){
		weekList = new ArrayList<Week>();
		
		//Week 1
		weekList.add(new Week("Week 1", "N/A", "N/A", "N/A", "Not Yet Pregnent...", "Quit Smoking. \n\nIf you smoke, you are exposing your wife and unborn child to secondhand smoke. This smoke isn't good for your partner or the baby. It can hurt the baby when it's inside your partner's uterus and after birth. Also, pregnant women who smoke are more likely to have babies born too small and too soon. If you both smoke or even if one of you smokes, now is a great time to quit. Get help from your provider or groups such as the American Cancer Society. ", R.drawable.week1));

		//Week 2
		weekList.add(new Week("Week 2", "N/A", "N/A", "N/A", "Not Yet Pregnant... ", "Get to Work\n\nEat you Wheaties, plan a romantic weekend, light some candles, and turn on the mood music... it's go time!", R.drawable.week2));

		//Week 3
		weekList.add(new Week("Week 3", "Poppyseed", "N/A", "N/A", "Sperm meets egg within the fallopian tube and the miracle of life begins. The fertilized egg then begins its journey towards the uterus.", "Learn as much as you can about pregnancy and childbirth. \n\nWatch, listen, browse and read. Watch videotapes, listen to audiotapes, check out the internet, or read books about pregnancy, childbirth and being a parent. You'll get brownie points from your partner for taking the time to learn about what she is going through and being able to give intelligent opinions.", R.drawable.week3));

		//Week 4
		weekList.add(new Week("Week 4", "Poppyseed", "N/A", "N/A", "Your little zygote or blastocyst is settling into her uterus...", "Be supportive. Use the buddy system to avoid alcohol. \n\nHelp her stay away from alcohol. It's best for women not to drink any alcohol during pregnancy because it can cause birth defects. Help your partner stay away from beer, wine, wine coolers, liquor and mixed drinks. You can help by giving her healthy juices and water to drink or by making fun nonalcoholic drinks together. If your partner drinks a lot of alcohol and can't stop, get help for her. ", R.drawable.week4));

		//Week 5
		weekList.add(new Week("Week 5", "Appleseed", ".13 in", "N/A", "Major organs and systems are forming...", "'Be Respectful of her desire for Intercourse' \n\nOn our first prenatal appointment our doctor pulled me aside and gave me this valuable tidbit.  Her desire for sex may change as her body changes. Many people find that sex feels different during pregnancy. As her belly gets bigger, try different positions. Find one that's comfortable for both of you. Talk to each other about what feels good. Remember, as long as your health care provider says it's okay, it's safe to have sex during pregnancy. It won't hurt the baby.", R.drawable.week5));

		//Week 6
		weekList.add(new Week("Week 6", "Sweet Pea", ".25 in", "N/A", "Blood is starting to circulate... ", "Be sympathetic to her morning sickness\n\nMany women begin to experience morning sickness, which can actually strike at any time during the day, during week six.  Help her however she needs it, bring her crackers or ginger ale, and try to keep strong smells which trigger her symptoms as far away from her as needed. ", R.drawable.week6));

		//Week 7
		weekList.add(new Week("Week 7", "Blueberry", ".51 in", "N/A", "Baby's brain is growing fast...", "What about that \'Maternal Glow\' I've always heard about?\n\nYour wife's body is experiencing many changes, which may result in acne.  Your wife may be feeling rather self concious, take a minute to remind her how beautifull you think she is. As a side note it may be a good time to consider purchasing a shiny gift for later down the road when her mood is really in the dumps.", R.drawable.week7));

		//Week 8
		weekList.add(new Week("Week 8", "Raspberry", ".63 in", ".04 oz", "Arms, legs, nose and lips are beginning to develop... ", "Go to prenatal care visits. \n\nYour spouse's first prenatal visit is likely this week. Your doctor will need to know your medical history, as well as your wife's. It's a good show of support, and you get to know the people who will be taking care of your partner and baby during the pregnancy.  Prior to your prenatal care vist, discuss questions you have with your spouse and be prepared to ask them during your visit. \n\nIf you are lucky you will see the first pictures of your unborn child, and may even see the flutter of a heartbeat during an ultrasound. Its an event best shared with your spouse, she will definately appreciate your involvement.", R.drawable.week8));

		//Week 9
		weekList.add(new Week("Week 9", "Green Olive", ".9 in", ".07 oz", "The head is roughly the same size as the body...", "Gotta go, Gotta go right now!\n\nYour wife may be making more frequent trips to the restroom, now is probably not the best time to tease her about this as it will likely be a conversation that goes south rather quickly.", R.drawable.week9));

		//Week 10
		weekList.add(new Week("Week 10", "Prune", " 1.2 in", ".14 oz", "Arm joints are working, and soon legs will too...", "Let's talk about work\n\nIf your spouse has not told her coworkers yet, now may be a good time to develop a plan for maternity leave.  If your workplace offers patternity leave, it is a good time to begin planning your own course of action, finish up what projects you are working on and for those which you cannot finish in time, begin training coworkers to cover for you in your absence.", R.drawable.week10));

		//Week 11
		weekList.add(new Week("Week 11", "Lime", "1.6 in", ".25 oz", "Your Baby is now considered a Fetus...", "Prenatal Testing\n\nAre you worried about the health and well-being of your developing child?  This is a good time to talk to your doctor about benefits of prenatal testing.  Just remember to do your homework, you are not under any obligations to do any tests.", R.drawable.week11));

		//Week 12
		weekList.add(new Week("Week 12", "Plum", "2.1 in", ".49 oz", "Almost all vital systems are fully formed... ", "Ask her how she's feeling\n\nYour wife may not be experiencing morning sickness and the frequent trips to the bathroom may have decreased.  Take some time to see how she is feeling, take her on a date, remind her exactly what she was thinking three months ago...", R.drawable.week12));

		//Week 13
		weekList.add(new Week("Week 13", "Peach", "2.9 in", ".81 oz", "Eyes and ears are moving into position, the face looks more and more human...", "Review your budget\n\nMom should be feeling pretty good as you move into the second trimester.  The second trimester isn't as physically taxing as the first and third.  Now may be a good time to review your budget. Consider starting a college fund: \n\n$/Month for 18yrs\n$50   = $14k   - $20k \n$100 = $28k   - $40k\n$200 = $50k   - $75k.\n$400 = $115k - $160k", R.drawable.week13));

		//Week 14
		weekList.add(new Week("Week 14", "Lemon", "3.4 in", "1.5 oz", "The chin no longer rests on the chest, the hands begin to flex, all nourishment is coming from the placenta...", "Everybody Poops\n\nExcept maybe your wife.  Her hormones have relaxed her bowels and her uterus may be pressing on them resulting in constipation.  If you have taken over shopping or cooking while she watches the bun in the oven, make sure you're feeding her enough fiber.  If it becomes more of an issue make sure to consult your doctor.", R.drawable.week14));

		//Week 15
		weekList.add(new Week("Week 15", "Naval Orange", "4.0 in", "2.5 oz", "Legs are finally longer than arms, baby starts to kick...", "Go to prenatal care visits. \n\nDuring the second trimester (months 4-6), go with your partner if she needs an ultrasound (a test that uses sound waves to take a picture of the baby). You'll be able to see your baby's head, arms, hands, legs and feet. You may even find out the sex of your baby. Your baby will start to seem very real to you. ", R.drawable.week15));

		//Week 16
		weekList.add(new Week("Week 16", "Avocado", "4.6 in", "3.5 oz", "Eyebrows, lashes and hair are filling in... ", "Make a will\n\nYeah Yeah... it's not going to happen to you, but what if it does?  Make sure your affairs are in order in case the unspeakable occurs. Consider godparents, life insurance and anything else that will take care of your child in the event of your death... Ok that was a lot, go grab a beer and think it over. Just don't share with mom.", R.drawable.week16));

		//Week 17
		weekList.add(new Week("Week 17", "Onion", "5.1 in", "5.9 oz", "Skeleton is hardening, and fat is accumulating...", "Week 17 What to Do", R.drawable.week17));

		//Week 18
		weekList.add(new Week("Week 18", "Sweet Potato", "5.6 in", "6.7 oz", "Baby's moving like crazy -- feel anything yet?", "Week 18 What to Do", R.drawable.week18));

		//Week 19
		weekList.add(new Week("Week 19", "Mango", "6.0 in", "8.5 oz", "Vernix caseosa is coating baby's skin...", "Week 19 What to Do", R.drawable.week19));

		//Week 20
		weekList.add(new Week("Week 20", "Cantaloupe", "6.5 in", "10.6 oz", "Boy or girl, genitals are fully formed...", "Week 20 What to Do", R.drawable.week20));

		//Week 21
		weekList.add(new Week("Week 21", "Papaya", "10.5-11.8 in", "12.7-20.8 oz", "Nipples are sprouting, and face is fully formed!", "Week 21 What to Do", R.drawable.week21));

		//Week 22
		weekList.add(new Week("Week 22", "Papaya", "10.5-11.8 in", "12.7-20.8 oz", "Nipples are sprouting, and face is fully formed!", "Week 22 What to Do", R.drawable.week22));

		//Week 23
		weekList.add(new Week("Week 23", "Papaya", "10.5-11.8 in", "12.7-20.8 oz", "Nipples are sprouting, and face is fully formed!", "Week 23 What to Do", R.drawable.week23));

		//Week 24
		weekList.add(new Week("Week 24", "Papaya", "10.5-11.8 in", "12.7-20.8 oz", "Nipples are sprouting, and face is fully formed!", "Week 24 What to Do", R.drawable.week24));

		//Week 25
		weekList.add(new Week("Week 25", "Eggplant", "13.6-14.8 in", "1.5-2.2 lb", "Immune system is preparing for the outside world... ", "Week 25 What to Do", R.drawable.week25));

		//Week 26
		weekList.add(new Week("Week 26", "Eggplant", "13.6-14.8 in", "1.5-2.2 lb", "Immune system is preparing for the outside world... ", "Week 26 What to Do", R.drawable.week26));

		//Week 27
		weekList.add(new Week("Week 27", "Eggplant", "13.6-14.8 in", "1.5-2.2 lb", "Immune system is preparing for the outside world... ", "Week 27 What to Do", R.drawable.week27));

		//Week 28
		weekList.add(new Week("Week 28", "Eggplant", "13.6-14.8 in", "1.5-2.2 lb", "Immune system is preparing for the outside world... ", "Week 28 What to Do", R.drawable.week28));

		//Week 29
		weekList.add(new Week("Week 29", "Squash", "15.2-16.7 in", "2.5-3.8 lb", "Sleeping and waking cycles are establishing...", "Go to prenatal care visits \n\nDuring a third-trimester (months 7Ð9) prenatal visit, ask the provider how you can help during labor and delivery.", R.drawable.week29));

		//Week 30
		weekList.add(new Week("Week 30", "Squash", "15.2-16.7 in", "2.5-3.8 lb", "Sleeping and waking cycles are establishing...", "Go to childbirth education classes with your partner. \n\nYou will learn how to help your partner during labor and delivery. Ask the doctor, midwife, nurse or local hospital or clinic about childbirth classes near you. ", R.drawable.week30));

		//Week 31
		weekList.add(new Week("Week 31", "Squash", "15.2-16.7 in", "2.5-3.8 lb", "Sleeping and waking cycles are establishing...", "Support your partner's decision to breastfeed. Breastmilk is the best food for your baby. It has everything that your baby needs to grow and be healthy. Find out about breastfeeding together. Talk to your doctor, midwife or nurse about breastfeeding", R.drawable.week31));

		//Week 32
		weekList.add(new Week("Week 32", "Squash", "15.2-16.7 in", "2.5-3.8 lb", "Sleeping and waking cycles are establishing...", "Help plan for the baby. \n\nTalk to your partner about what you both want for the baby. Decide where the baby will sleep, and make that part of your home colorful and welcoming for the baby. Go shopping for baby things. \n\nIf you are worried about not having enough money, here are some tips to help you:\n-Ask family members and friends if you can borrow a crib, changing table, toys and baby clothes. Many people are between kids or don't plan to have any more kids and are glad to let you use their baby things.\n-Check out secondhand and thrift shops. They often have baby furniture, toys and clothes at low prices.\n-Put a small amount of money aside each week to help pay for baby things. Even $10 a week can add up to make things easier once the baby comes.", R.drawable.week32));

		//Week 33
		weekList.add(new Week("Week 33", "Honeydew", "17.2-18.7 in", "4.2-5.8 lb", "Growth is slowing, and baby may soon descend... ", "Week 33 What to Do", R.drawable.week33));

		//Week 34
		weekList.add(new Week("Week 34", "Honeydew", "17.2-18.7 in", "4.2-5.8 lb", "Growth is slowing, and baby may soon descend... ", "Week 34 What to Do", R.drawable.week34));

		//Week 35
		weekList.add(new Week("Week 35", "Honeydew", "17.2-18.7 in", "4.2-5.8 lb", "Growth is slowing, and baby may soon descend... ", "Week 35 What to Do", R.drawable.week35));

		//Week 36
		weekList.add(new Week("Week 36", "Honeydew", "17.2-18.7 in", "4.2-5.8 lb", "Growth is slowing, and baby may soon descend... ", "Week 36 What to Do", R.drawable.week36));

		//Week 37
		weekList.add(new Week("Week 37", "Watermelon", "18.9-20.9 in", "6.2-9.2 lb", "Full term! Baby's finally ready for the outside world...", "Week 37 What to Do", R.drawable.week37));

		//Week 38
		weekList.add(new Week("Week 38", "Watermelon", "18.9-20.9 in", "6.2-9.2 lb", "Full term! Baby's finally ready for the outside world...", "Week 38 What to Do", R.drawable.week38));

		//Week 39
		weekList.add(new Week("Week 39", "Watermelon", "18.9-20.9 in", "6.2-9.2 lb", "Full term! Baby's finally ready for the outside world...", "Week 39 What to Do", R.drawable.week39));

		//Week 40
		weekList.add(new Week("Week 40", "Watermelon", "18.9-20.9 in", "6.2-9.2 lb", "Full term! Baby's finally ready for the outside world...", "Week 40 What to Do", R.drawable.week40));
	}
	

	
	

	


		
		
}
