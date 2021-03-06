/**
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the
 * specific language governing rights and limitations under the License.
 *
 * The Original Code is "Field.java".  Description:
 * Auxilliary model class encapsulating field repetitions and substructures
 *
 * The Initial Developer of the Original Code is University Health Network. Copyright (C)
 * 2001-2013.  All Rights Reserved.
 *
 * Contributor(s): ______________________________________.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * GNU General Public License (the  �GPL�), in which case the provisions of the GPL are
 * applicable instead of those above.  If you wish to allow use of your version of this
 * file only under the terms of the GPL and not to allow others to use your version
 * of this file under the MPL, indicate your decision by deleting  the provisions above
 * and replace  them with the notice and other provisions required by the GPL License.
 * If you do not delete the provisions above, a recipient may use your version of
 * this file under either the MPL or the GPL.
 *
 */
package ca.uhn.hl7v2.model;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.Location;

/**
 * Field is a auxiliary model class only created while visiting parts of the
 * message.
 */
public class Field implements Visitable {

    private final Type[] reps;
    private final int maxCardinality;
    
    
    public Field(Type[] reps, int maxCardinality) {
        super();
        this.reps = reps;
        this.maxCardinality = maxCardinality;
    }

    public boolean accept(MessageVisitor visitor, Location currentLocation) throws HL7Exception {
        if (visitor.start(this, currentLocation)) {
            for (int i=0; i < reps.length; i++) {
                Type t = reps[i];
                Location nextLocation = currentLocation;
                if (reps.length > 1) {
                  nextLocation = provideLocation(currentLocation, currentLocation.getField(), i);
                }
                if (!t.accept(visitor, nextLocation))
                    break;
            }
        }
        return visitor.end(this, currentLocation);
    }

    public Location provideLocation(Location parentLocation, int index, int repetition) {
        return new Location(parentLocation).withField(index).withFieldRepetition(repetition);
    }

    public boolean isEmpty() {
        return reps.length == 0;
    }

}
