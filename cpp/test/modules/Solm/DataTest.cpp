/**
 * RQDQL.com, Requirements Definition and Query Language
 *
 * Redistribution and use in source and binary forms, with or 
 * without modification, are PROHIBITED without prior written 
 * permission from the author. This product may NOT be used 
 * anywhere and on any computer except the server platform of 
 * rqdql.com. located at www.rqdql.com. If you received this 
 * code occasionally and without intent to use it, please report 
 * this incident to the author by email: team@rqdql.com
 *
 * @author Yegor Bugayenko <egor@tpc2.com>
 * @copyright Copyright (c) rqdql.com, 2010
 * @version $Id$
 */

#include <boost/test/unit_test.hpp>
#include "Solm/Data.h"
using namespace solm;

BOOST_AUTO_TEST_SUITE(DataTest)

BOOST_AUTO_TEST_CASE(testEmptyDataIsPositive) {
    Data d;
    BOOST_CHECK((bool)d);
}

BOOST_AUTO_TEST_SUITE_END()
