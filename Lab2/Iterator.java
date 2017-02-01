

import java.io.*;
import java.util.*;

interface Iterator {
	  int get();
	  Iterator advance();
	  boolean equals(Iterator other);
	}