package com.example.android.bakingapp;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.core.IsNot.not;


/**
 * This test demos that when opening Detail View on a Tablet, the Step detail fragment doensn't have buttons.
 *
 * This test if for Tablet only.
 *
 */

@RunWith(AndroidJUnit4.class)
public class Tablet_StepDetailFragmentBasicTest {

    private final String recipeJsonExtra = "{\"id\":1,\"image\":\"\",\"ingredients\":[{\"ingredient\":\"Graham Cracker crumbs\",\"measure\":\"CUP\",\"quantity\":2.0},{\"ingredient\":\"unsalted butter, melted\",\"measure\":\"TBLSP\",\"quantity\":6.0},{\"ingredient\":\"granulated sugar\",\"measure\":\"CUP\",\"quantity\":0.5},{\"ingredient\":\"salt\",\"measure\":\"TSP\",\"quantity\":1.5},{\"ingredient\":\"vanilla\",\"measure\":\"TBLSP\",\"quantity\":5.0},{\"ingredient\":\"Nutella or other chocolate-hazelnut spread\",\"measure\":\"K\",\"quantity\":1.0},{\"ingredient\":\"Mascapone Cheese(room temperature)\",\"measure\":\"G\",\"quantity\":500.0},{\"ingredient\":\"heavy cream(cold)\",\"measure\":\"CUP\",\"quantity\":1.0},{\"ingredient\":\"cream cheese(softened)\",\"measure\":\"OZ\",\"quantity\":4.0}],\"name\":\"Nutella Pie\",\"servings\":8,\"steps\":[{\"id\":0,\"description\":\"Recipe Introduction\",\"shortDescription\":\"Recipe Introduction\",\"thumbnailURL\":\"\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\"},{\"id\":1,\"description\":\"1. Preheat the oven to 350°F. Butter a 9\\\" deep dish pie pan.\",\"shortDescription\":\"Starting prep\",\"thumbnailURL\":\"\",\"videoURL\":\"\"},{\"id\":2,\"description\":\"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\"shortDescription\":\"Prep the cookie crust.\",\"thumbnailURL\":\"\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\"},{\"id\":3,\"description\":\"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\"shortDescription\":\"Press the crust into baking form.\",\"thumbnailURL\":\"\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\"},{\"id\":4,\"description\":\"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\"shortDescription\":\"Start filling prep\",\"thumbnailURL\":\"\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\"},{\"id\":5,\"description\":\"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\"shortDescription\":\"Finish filling prep\",\"thumbnailURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\",\"videoURL\":\"\"},{\"id\":6,\"description\":\"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it\\u0027s ready to serve!\",\"shortDescription\":\"Finishing Steps\",\"thumbnailURL\":\"\",\"videoURL\":\"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\"}]}";
    private final String DESCRIPTION ="Recipe Introduction";

    @Rule
    public ActivityTestRule<DetailActivity> mActivityTestRule =
            new ActivityTestRule<>(DetailActivity.class, false, false);


    @Before
    public void setup() {
        Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), DetailActivity.class);
        //intent.putExtra(StepDetailActivity.STEP_EXTRA, stepJsonExtra);
        intent.putExtra(DetailActivity.RECIPE_EXTRA, recipeJsonExtra);

        mActivityTestRule.launchActivity(intent);
    }

    @Test
    public void clickNextButton() {

        onView(withId(R.id.step_description_tv)).check(matches(withText(DESCRIPTION)));

        onView(withId(R.id.button_previous)).check(matches(not(isDisplayed())));

        onView(withId(R.id.button_next)).check(matches(not(isDisplayed())));
    }
}
