package it.ck.cyberdeck.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import it.ck.cyberdeck.persistance.FileSystemLibraryCardGateway;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

public class CardLibraryTest {

  private CardLibrary cl;

  @Before
  public void setup() {
    cl = getCardLibrary();
  }

  @Test
  public void test() throws Exception {
    assertThat(cl.size(), is(equalTo(233)));
    assertThat(cl.getCardList().size(), is(equalTo(233)));
  }

  private CardLibrary getCardLibrary() {
    FileSystemLibraryCardGateway loader = new FileSystemLibraryCardGateway();
    CardLibrary cl = new CardLibrary(loader);
    return cl;
  }

  @Test
  public void eachCardHasSet(){
	  for (Card card : cl.getCardList()) {
		  assertThat(card.getSet(), is(notNullValue()));
		
	}
  }
  
  @Test
  public void eachCardHasAFurl() throws Exception {
    for (Card card : cl.getCardList()) {
      assertThat(card.getImageName(), is(not(emptyString())));
    }
  }

  @Test
  public void eachCardHasAName() throws Exception {
    for (Card card : cl.getCardList()) {
      assertThat(card.getName(), is(not(emptyString())));
    }
  }

  protected Matcher<? super String> emptyString() {
    return new TypeSafeMatcher<String>() {

      @Override
      public void describeTo(Description description) {
        description.appendText("the string is not empty");

      }

      @Override
      protected boolean matchesSafely(String item) {
        return StringUtils.isEmpty(item);
      }

    };
  }
}
