package src.main.poker.models;

public class Poker
{
    public static final int STRAIGHT_FLUSH    = 8000000;
    public static final int FOUR_OF_A_KIND = 7000000;
    public static final int FULL_HOUSE     = 6000000;
    public static final int FLUSH          = 5000000;
    public static final int STRAIGHT       = 4000000;
    public static final int SET            = 3000000;
    public static final int TWO_PAIRS      = 2000000;
    public static final int ONE_PAIR       = 1000000;

    public static int valueHand( Card[] h ) {
        if ( isFlush(h) && isStraight(h) )
            return valueStraightFlush(h);
        else if ( is4s(h) )
            return valueFourOfAKind(h);
        else if ( isFullHouse(h) )
            return valueFullHouse(h);
        else if ( isFlush(h) )
            return valueFlush(h);
        else if ( isStraight(h) )
            return valueStraight(h);
        else if ( is3s(h) )
            return valueSet(h);
        else if ( is22s(h) )
            return valueTwoPairs(h);
        else if ( is2s(h) )
            return valueOnePair(h);
        else
            return valueHighCard(h);
    }

    public static int valueStraightFlush( Card[] h ) {
        return STRAIGHT_FLUSH + valueHighCard(h);
    }

    public static int valueFlush( Card[] h ) {
        return FLUSH + valueHighCard(h);
    }

    public static int valueStraight( Card[] h ) {
        return STRAIGHT + valueHighCard(h);
    }

    public static int valueFourOfAKind( Card[] h ) {
        sortByRank(h);

        return FOUR_OF_A_KIND + h[2].getRank();
    }

    public static int valueFullHouse( Card[] h ) {
        sortByRank(h);

        return FULL_HOUSE + h[2].getRank();
    }

    public static int valueSet( Card[] h ) {
        sortByRank(h);

        return SET + h[2].getRank();
    }

    public static int valueTwoPairs( Card[] h ) {
        int val = 0;

        sortByRank(h);

        if ( h[0].getRank() == h[1].getRank() &&
                h[2].getRank() == h[3].getRank() )
            val = 14*14*h[2].getRank() + 14*h[0].getRank() + h[4].getRank();
        else if ( h[0].getRank() == h[1].getRank() &&
                h[3].getRank() == h[4].getRank() )
            val = 14*14*h[3].getRank() + 14*h[0].getRank() + h[2].getRank();
        else
            val = 14*14*h[3].getRank() + 14*h[1].getRank() + h[0].getRank();

        return TWO_PAIRS + val;
    }

    public static int valueOnePair( Card[] h ) {
        int val = 0;

        sortByRank(h);

        if ( h[0].getRank() == h[1].getRank() )
            val = 14*14*14*h[0].getRank() +
                    + h[2].getRank() + 14*h[3].getRank() + 14*14*h[4].getRank();
        else if ( h[1].getRank() == h[2].getRank() )
            val = 14*14*14*h[1].getRank() +
                    + h[0].getRank() + 14*h[3].getRank() + 14*14*h[4].getRank();
        else if ( h[2].getRank() == h[3].getRank() )
            val = 14*14*14*h[2].getRank() +
                    + h[0].getRank() + 14*h[1].getRank() + 14*14*h[4].getRank();
        else
            val = 14*14*14*h[3].getRank() +
                    + h[0].getRank() + 14*h[1].getRank() + 14*14*h[2].getRank();

        return ONE_PAIR + val;
    }

    public static int valueHighCard( Card[] h ) {
        int val;

        sortByRank(h);

        val = h[0].getRank() + 14* h[1].getRank() + 14*14* h[2].getRank()
                + 14*14*14* h[3].getRank() + 14*14*14*14* h[4].getRank();

        return val;
    }

    public static boolean is4s( Card[] h ) {
        boolean a1, a2;

        if ( h.length != 5 )
            return(false);

        sortByRank(h);

        a1 = h[0].getRank() == h[1].getRank() &&
                h[1].getRank() == h[2].getRank() &&
                h[2].getRank() == h[3].getRank() ;

        a2 = h[1].getRank() == h[2].getRank() &&
                h[2].getRank() == h[3].getRank() &&
                h[3].getRank() == h[4].getRank() ;

        return( a1 || a2 );
    }

    public static boolean isFullHouse( Card[] h ) {
        boolean a1, a2;

        if ( h.length != 5 )
            return(false);

        sortByRank(h);

        a1 = h[0].getRank() == h[1].getRank() &&  //  x x x y y
                h[1].getRank() == h[2].getRank() &&
                h[3].getRank() == h[4].getRank();

        a2 = h[0].getRank() == h[1].getRank() &&  //  x x y y y
                h[2].getRank() == h[3].getRank() &&
                h[3].getRank() == h[4].getRank();

        return( a1 || a2 );
    }

    public static boolean is3s( Card[] h ) {
        boolean a1, a2, a3;

        if ( h.length != 5 )
            return(false);

        if ( is4s(h) || isFullHouse(h) )
            return(false);
        sortByRank(h);

        a1 = h[0].getRank() == h[1].getRank() &&
                h[1].getRank() == h[2].getRank() ;

        a2 = h[1].getRank() == h[2].getRank() &&
                h[2].getRank() == h[3].getRank() ;

        a3 = h[2].getRank() == h[3].getRank() &&
                h[3].getRank() == h[4].getRank() ;

        return( a1 || a2 || a3 );
    }

    public static boolean is22s( Card[] h ) {
        boolean a1, a2, a3;

        if ( h.length != 5 )
            return(false);

        if ( is4s(h) || isFullHouse(h) || is3s(h) )
            return(false);

        sortByRank(h);

        a1 = h[0].getRank() == h[1].getRank() &&
                h[2].getRank() == h[3].getRank() ;

        a2 = h[0].getRank() == h[1].getRank() &&
                h[3].getRank() == h[4].getRank() ;

        a3 = h[1].getRank() == h[2].getRank() &&
                h[3].getRank() == h[4].getRank() ;

        return( a1 || a2 || a3 );
    }

    public static boolean is2s( Card[] h ) {
        boolean a1, a2, a3, a4;

        if ( h.length != 5 )
            return(false);

        if ( is4s(h) || isFullHouse(h) || is3s(h) || is22s(h) )
            return(false);        // The hand is not one pair (but better)

        sortByRank(h);

        a1 = h[0].getRank() == h[1].getRank() ;
        a2 = h[1].getRank() == h[2].getRank() ;
        a3 = h[2].getRank() == h[3].getRank() ;
        a4 = h[3].getRank() == h[4].getRank() ;

        return( a1 || a2 || a3 || a4 );
    }

    public static boolean isFlush( Card[] h ) {
        if ( h.length != 5 )
            return(false);

        sortBySuit(h);

        return( h[0].getSuit() == h[4].getSuit() );
    }

    public static boolean isStraight( Card[] h ) {
        int i, testRank;

        if ( h.length != 5 )
            return(false);

        sortByRank(h);
        if ( h[4].getRank() == 14 ) {
            boolean a = h[0].getRank() == 2 && h[1].getRank() == 3 &&
                    h[2].getRank() == 4 && h[3].getRank() == 5 ;
            boolean b = h[0].getRank() == 10 && h[1].getRank() == 11 &&
                    h[2].getRank() == 12 && h[3].getRank() == 13 ;

            return ( a || b );
        } else {
            testRank = h[0].getRank() + 1;

            for ( i = 1; i < 5; i++ )
            {
                if ( h[i].getRank() != testRank )
                    return(false);

                testRank++;
            }

            return(true);
        }
    }

    public static void sortByRank( Card[] h ) {
        int i, j, min_j;
        for ( i = 0 ; i < h.length ; i ++ )
        {
            min_j = i;

            for ( j = i+1 ; j < h.length ; j++ )
            {
                if ( h[j].getRank() < h[min_j].getRank() )
                {
                    min_j = j;
                }
            }
            Card help = h[i];
            h[i] = h[min_j];
            h[min_j] = help;
        }
    }

    public static void sortBySuit( Card[] h ) {
        int i, j, min_j;
        for ( i = 0 ; i < h.length ; i ++ )
        {
            min_j = i;

            for ( j = i+1 ; j < h.length ; j++ )
            {
                if ( h[j].getSuit() < h[min_j].getSuit() )
                {
                    min_j = j;
                }
            }
            Card help = h[i];
            h[i] = h[min_j];
            h[min_j] = help;
        }
    }
}
