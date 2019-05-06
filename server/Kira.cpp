#include <stdlib.h>

int main ()
{
	if (!system(NULL)) 
	{
		return 1;
	}
	system("cd server && server.bat");
	return 0;
}