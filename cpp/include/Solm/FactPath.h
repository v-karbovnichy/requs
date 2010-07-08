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

#ifndef __INCLUDE_SOLM_FACTPATH_H
#define __INCLUDE_SOLM_FACTPATH_H

#include <vector>
#include <string>
#include "Solm/Fact.h"

namespace solm {

/**
 * Serie of facts, ordered
 */
class FactPath : public Fact, public std::vector<Fact> {

public:

    /**
     * The path ends positively?
     */
    operator bool() const;

    /**
     * Concatenate two paths
     */
    FactPath operator+(const FactPath&) const;

    /**
     * These two paths are equivalent?
     */
    bool operator==(const FactPath&) const;

    /**
     * One path is shorter than the other?
     */
    bool operator<(const FactPath&) const;

    /**
     * Convert this path to a user-friendly string
     */
    operator std::string() const;

private:

};

}

#endif
