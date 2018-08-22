/*
 * Copyright 2017-2018 Blue Lotus Software, LLC.
 * Copyright 2017-2018 John Yeary <jyeary@bluelotussoftware.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bluelotussoftware.tomcat.embedded;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * An Example Embedded Apache Tomcat with an anonymous inner class
 * {@link HttpServlet}.
 *
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @version 1.0.0
 */
public class Main {

    /**
     * Main method.
     *
     * @param args command line arguments passed to the application. Currently
     * unused.
     * @throws LifecycleException If a life cycle exception occurs.
     * @throws InterruptedException If the application is interrupted while
     * waiting for requests.
     * @throws ServletException If the servlet handling the response has an
     * exception.
     */
    public static void main(String[] args)
            throws LifecycleException, InterruptedException, ServletException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());

        Tomcat.addServlet(ctx, "hello", new HttpServlet() {
            private static final long serialVersionUID = 3600060857537422698L;

            @Override
            protected void service(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain");
                try (Writer writer = response.getWriter()) {
                    writer.write("Hello, Embedded World from Blue Lotus Software!");
                    writer.flush();
                }
            }
        });
        ctx.addServletMappingDecoded("/*", "hello");

        tomcat.start();
        tomcat.getServer().await();
    }

}
