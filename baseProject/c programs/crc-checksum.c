#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>

#define CRC32MASKREV   0xEDB88320            /* CRC-32 Bitmask*/

int      bitstream[] = { 1,0,0,0,1,1,0,0 };  /* ASCII-"1", LSB first */
int      bitcount    = 8;
uint32_t crc32_rev   = ~0;

int main(void)
{
    int i;
    for (i = 0; i < bitcount; i++)
    {
        if ((crc32_rev & 1) != bitstream[i])
            crc32_rev = (crc32_rev >> 1) ^ CRC32MASKREV;
        else
            crc32_rev = (crc32_rev >> 1);
    }
    printf("0x%08X\n", ~crc32_rev);          /* MSB first */
    return EXIT_SUCCESS;
}
