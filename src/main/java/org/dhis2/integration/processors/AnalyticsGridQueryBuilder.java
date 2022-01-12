/*
 * Copyright (c) 2004-2022, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.dhis2.integration.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.dhis2.integration.model.ProgramIndicator;
import org.dhis2.integration.routes.T2ARouter;
import org.dhis2.integration.routes.T2ARouterNew;

public class AnalyticsGridQueryBuilder implements Processor
{
    public void process( Exchange exchange )
    {
        ProgramIndicator programIndicator = exchange.getMessage().getBody( ProgramIndicator.class );

        String ouLevel = exchange.getProperty( T2ARouter.PROPERTY_OU_LEVEL, String.class );
        String period = exchange.getProperty( T2ARouter.PROPERTY_PERIOD, String.class );

        // builder
        String query = "dimension=dx:" + programIndicator.getId() +
            "&dimension=ou:LEVEL-" + ouLevel +
            "&dimension=pe:" + period +
            "&rows=ou;pe&columns=dx&skipMeta=true";

        // set program indicator of this route to use later
        exchange.setProperty( T2ARouterNew.PROPERTY_PROGRAM_INDICATOR, programIndicator );
        exchange.getMessage().setHeader( Exchange.HTTP_QUERY, query );
    }
}
