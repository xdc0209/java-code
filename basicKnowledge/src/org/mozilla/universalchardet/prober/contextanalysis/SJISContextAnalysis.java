/*
 * ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Mozilla Communicator client code.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1998
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * Kohei TAKETA <k-tak@void.in> (Java port)
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK *****
 */

package org.mozilla.universalchardet.prober.contextanalysis;

public class SJISContextAnalysis extends JapaneseContextAnalysis
{
    ////////////////////////////////////////////////////////////////
    // constants
    ////////////////////////////////////////////////////////////////
    public static final int HIRAGANA_HIGHBYTE      = 0x82;
    public static final int HIRAGANA_LOWBYTE_BEGIN = 0x9F;
    public static final int HIRAGANA_LOWBYTE_END   = 0xF1;
    public static final int HIGHBYTE_BEGIN_1       = 0x81;
    public static final int HIGHBYTE_END_1         = 0x9F;
    public static final int HIGHBYTE_BEGIN_2       = 0xE0;
    public static final int HIGHBYTE_END_2         = 0xEF;

    ////////////////////////////////////////////////////////////////
    // methods
    ////////////////////////////////////////////////////////////////
    public SJISContextAnalysis()
    {
        super();
    }

    @Override
    protected void getOrder(Order order, final byte[] buf, int offset)
    {
        order.order = -1;
        order.charLength = 1;

        int highbyte = buf[offset] & 0xFF;
        if ((highbyte >= HIGHBYTE_BEGIN_1 && highbyte <= HIGHBYTE_END_1)
                || (highbyte >= HIGHBYTE_BEGIN_2 && highbyte <= HIGHBYTE_END_2))
        {
            order.charLength = 2;
        }

        if (highbyte == HIRAGANA_HIGHBYTE)
        {
            int lowbyte = buf[offset + 1] & 0xFF;
            if (lowbyte >= HIRAGANA_LOWBYTE_BEGIN && lowbyte <= HIRAGANA_LOWBYTE_END)
            {
                order.order = (lowbyte - HIRAGANA_LOWBYTE_BEGIN);
            }
        }
    }

    @Override
    protected int getOrder(final byte[] buf, int offset)
    {
        int highbyte = buf[offset] & 0xFF;
        if (highbyte == HIRAGANA_HIGHBYTE)
        {
            int lowbyte = buf[offset + 1] & 0xFF;
            if (lowbyte >= HIRAGANA_LOWBYTE_BEGIN && lowbyte <= HIRAGANA_LOWBYTE_END)
            {
                return (lowbyte - HIRAGANA_LOWBYTE_BEGIN);
            }
        }

        return -1;
    }
}
