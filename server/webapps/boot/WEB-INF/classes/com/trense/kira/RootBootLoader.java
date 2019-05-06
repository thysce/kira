package com.trense.kira;

import javax.servlet.http.HttpServlet;
import javax.servlet.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import javax.servlet.annotation.WebListener;

@WebListener
public class RootBootLoader implements ServletContextListener {

    @Override
    public void contextInitialized( ServletContextEvent event ) {
        System.out.println( "/boot contextInitialized()" );
        final File deployed = new File( "webapps/ROOT.war" );
        if ( deployed.exists() ) {
            System.out.println( "/boot contextInitialized Deployed Root exists. Deleting..." );
            boolean ok = deployed.delete();
            System.out.println( "/boot contextInitialized Deleted=" + ok );
        }
        final File clean = new File( "app.war" );
        try {
            System.out.println( "/boot contextInitialized Copying deployment..." );
            Path deploypath = Files.copy( clean.toPath(), deployed.toPath() );
            System.out.println( "/boot contextInitialized Copied deployment to " + deploypath );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }


        System.out.println( "/boot contextInitialized() starting browser" );

        ProcessBuilder startBrowser = new ProcessBuilder();
        startBrowser.command( "cmd", "/c", "open-browser.bat" );
        try {
            startBrowser.start();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }

        System.out.println( "/boot contextInitialized() Complete" );
    }

    @Override
    public void contextDestroyed( ServletContextEvent event ) {
        System.out.println( "/boot contextDestroyed()" );
        final File deployed = new File( "webapps/ROOT.war" );
        final File deployedExploded = new File( "webapps/ROOT" );
        if ( deployed.exists() ) {
            System.out.println( "/boot contextDestroyed Deployed Root exists. Deleting..." );
            boolean ok = deployed.delete();
            System.out.println( "/boot contextDestroyed Deleted WAR : " + ok );
        }
        if ( deployedExploded.exists() ) {
            System.out.println( "/boot contextDestroyed Deployed Exploded Root exists. Deleting..." );
            try {
                Files.walkFileTree( deployedExploded.toPath(), DELETE_ALL_VISITOR );
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
            System.out.println( "/boot contextDestroyed Deleted Exploded" );
        }
        final File temp = new File( System.getProperty( "java.io.tmpdir" ) );
        if ( temp.exists() && new File( temp.getParentFile(), "app.war" ).exists() ) {
            System.out.println( "/boot contextDestroyed temp-dir exists. Deleting content..." );
            for ( File f : temp.listFiles() ) {
                try {
                    System.out.println( "/boot contextDestroyed Deleting temp content " + f.getName() );
                    if ( f.isDirectory() ) {
                        Files.walkFileTree( f.toPath(), DELETE_ALL_VISITOR );
                    }
                    else {
                        f.delete();
                    }
                }
                catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
            System.out.println( "/boot contextDestroyed Deleted temp-dir content" );
        }
        System.out.println( "/boot contextDestroyed() Complete" );
    }

    private static SimpleFileVisitor<Path> DELETE_ALL_VISITOR = new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile( Path file, BasicFileAttributes attr ) throws IOException {
            Files.delete( file );
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory( Path file, IOException e ) throws IOException {
            if ( e != null ) {
                throw e;
            }
            Files.delete( file );
            return FileVisitResult.CONTINUE;
        }
    };
}