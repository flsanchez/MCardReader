package com.example.cardreader;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static com.example.cardreader.utils.CustomMatchers.withImageDrawable;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;


import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class CardListFragmentUnitTest {
    @Mock CardViewModel cardViewModel;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private FragmentScenario<CardListFragment> fragmentScenario;

    private final String MOCKED_CARD_TEXT = "test_text";
    private final String MOCKED_CARD_NAME = "test_name";
    private final String MOCKED_CARD_SCRYFALL_ID = "test_scryfall_id";


    private void mockCard(Card card){
        when(card.getText()).thenReturn(MOCKED_CARD_TEXT);
        when(card.getName()).thenReturn(MOCKED_CARD_NAME);
        when(card.getScryfallId()).thenReturn(MOCKED_CARD_SCRYFALL_ID);
    }

    private ArrayList<Card> createCardList() {
        Card card = mock(Card.class);
        mockCard(card);
        return new ArrayList<>(Arrays.asList(card));
    }

    @Before
    public void setUp() {
        setUpCardViewModel();
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    private void setUpCardViewModel() {
        MutableLiveData<List<Card>> cardList = new MutableLiveData<>();
        cardList.postValue(createCardList());
        when(cardViewModel.getCardList(any())).thenReturn(cardList);
    }

    @Test
    public void whenLoadedCardListThenCardIsShowing(){
        launchFragment(true);

        assertVisibility(withText(MOCKED_CARD_TEXT), VISIBLE);
    }

    @Test
    public void whenLoadedCardListIsAllThenFavoriteButtonIsVisible(){
        launchFragment(true);

        assertVisibility(withId(R.id.favorite_button), VISIBLE);
    }

    @Test
    public void whenLoadedCardListIsFavoriteThenFavoriteButtonIsNotVisible(){
        launchFragment(false);

        assertVisibility(withId(R.id.favorite_button), GONE);
    }

    @Test
    public void whenClickOnFavoriteButtonThenCardIsMarkedAsFavorite(){
        launchFragment(true);

        clickOn(R.id.favorite_button);

        assertDrawable(withId(R.id.favorite_button), android.R.drawable.btn_star_big_on);
    }

    @Test
    public void whenClickOnDisplayImageOnPortraitThenNewActivityIsLaunched(){
        launchFragment(true);

        clickButton(R.id.image_button);

        intended(hasComponent(CardDetailActivity.class.getName()));
    }

//    @Test
//    public void whenClickOnDisplayImageOnLandscapeThenImageShowsInSameView(){
//        launchFragment(true);
//
//        clickButton(R.id.image_button);
//
//        intended(hasComponent(CardDetailActivity.class.getName()));
//    }

    @Test
    public void whenCardListIsToggledToFavoriteThenCardListShouldDisplayFavorites(){
        launchFragment(true);

        fragmentScenario.onFragment(fragment -> fragment.toggleFavoriteList(false));

        assertVisibility(withId(R.id.favorite_button), GONE);
    }

    private void launchFragment(boolean showFavorite) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(CardListFragment.FAVORITE_PARAM, showFavorite);
        fragmentScenario = FragmentScenario.launchInContainer(
                CardListFragment.class,
                bundle,
                new FragmentFactory() {
                    @NonNull
                    @Override
                    public Fragment
                    instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
                        CardListFragment
                                fragment = (CardListFragment) super.instantiate(classLoader, className);
                        fragment.mCardViewModel = cardViewModel;
                        return fragment;
                    }
                });
//                }).
//                onFragment(
//                        fragment ->
//                        {fragment.getActivity().
//                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                            SystemClock.sleep(1000);
//                        });
    }

    private void clickButton(int resourceId) {
        onView(withId(resourceId)).check(matches(allOf(isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click image button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
    }

    private void assertVisibility(Matcher<View> viewMatcher, ViewMatchers.Visibility visibility) {
        onView(viewMatcher).check(matches(withEffectiveVisibility(visibility)));
    }

    private void assertDrawable(Matcher<View> viewMatcher, int drawableResourceId) {
        onView(viewMatcher).check(matches(withImageDrawable(drawableResourceId)));
    }

}
