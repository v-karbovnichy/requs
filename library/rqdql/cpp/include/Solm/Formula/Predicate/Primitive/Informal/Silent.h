/**
 *
 * FaZend.com, Fully Automated Zend Framework
 * RQDQL.com, Requirements Definition and Query Language
 *
 * Redistribution and use in source and binary forms, with or 
 * without modification, are PROHIBITED without prior written 
 * permission from the author. This product may NOT be used 
 * anywhere and on any computer except the server platform of 
 * FaZend.com. located at www.fazend.com. If you received this 
 * code occasionally and without intent to use it, please report 
 * this incident to the author by email: team@rqdql.com
 *
 * @author Yegor Bugayenko <egor@tpc2.com>
 * @copyright Copyright (c) rqdql.com, 2010
 * @version $Id$
 */

/**
 * Constructor
 */
Silent::Silent(const string& s) : Informal<Silent>() { 
    if (s.empty()) {
        throw rqdql::Exception(rqdql::_t("Empty arg for SILENT()"));
    }
    arg(s); 
}

/**
 * Create an outcome of this formula, list of facts
 */
Outcome Silent::getOutcome(const Fact& f, const Snapshot::Mapping& m = Snapshot::NullMapping) const { 
    Fact fact;
    fact.setFormula(this);
    
    Snapshot s = f.getSnapshot();
    Snapshot::Object& obj = s.create("silent");
    s.assignId(obj);
    obj.setValue("\"" + getVar().substr(1) + "\"");
    fact.setSnapshot(s);
    
    return Outcome() << fact; 
}