import {
    Bar,
    BarChart,
    CartesianGrid,
    ResponsiveContainer,
    Tooltip,
    XAxis,
    YAxis,
} from "recharts";

import type {
    PayoutBucket,
    SimulationResult,
} from "../types/simulation";

interface Props {
    result: SimulationResult;
}

const bucketLabels: Record<PayoutBucket, string> = {
    ZERO: "0×",

    OVER_0_TO_0_25X: ">0–0.25×",
    OVER_0_25_TO_0_5X: ">0.25–0.5×",
    OVER_0_5_TO_1X: ">0.5–1×",
    OVER_1_TO_2X: ">1–2×",
    OVER_2_TO_4X: ">2–4×",
    OVER_4_TO_8X: ">4–8×",
    OVER_8_TO_16X: ">8–16×",
    OVER_16_TO_32X: ">16–32×",
    OVER_32_TO_64X: ">32–64×",
    OVER_64_TO_128X: ">64–128×",
    OVER_128_TO_256X: ">128–256×",
    OVER_256_TO_512X: ">256–512×",
    OVER_512_TO_1024X: ">512–1024×",
    OVER_1024_TO_2048X: ">1024–2048×",
    OVER_2048_TO_4096X: ">2048–4096×",
    OVER_4096X: ">4096×",
};

const bucketOrder: PayoutBucket[] = [
    "ZERO",

    "OVER_0_TO_0_25X",
    "OVER_0_25_TO_0_5X",
    "OVER_0_5_TO_1X",
    "OVER_1_TO_2X",
    "OVER_2_TO_4X",
    "OVER_4_TO_8X",
    "OVER_8_TO_16X",
    "OVER_16_TO_32X",
    "OVER_32_TO_64X",
    "OVER_64_TO_128X",
    "OVER_128_TO_256X",
    "OVER_256_TO_512X",
    "OVER_512_TO_1024X",
    "OVER_1024_TO_2048X",
    "OVER_2048_TO_4096X",
    "OVER_4096X",
];

export function PayoutDistributionChart({
                                            result,
                                        }: Props) {

    const allData = bucketOrder.map((bucket) => {
        const cycles =
            result.payoutDistribution[bucket] ?? 0;

        const percentage =
            result.paidSpins > 0
                ? (cycles / result.paidSpins) * 100
                : 0;

        return {
            bucket: bucketLabels[bucket],
            cycles,
            percentage,
        };
    });

    // Remove empty buckets from the upper end,
    // while preserving empty gaps within the populated range.
    const lastPopulatedIndex =
        allData.findLastIndex(
            (item) => item.cycles > 0
        );

    const data =
        lastPopulatedIndex >= 0
            ? allData.slice(
                0,
                lastPopulatedIndex + 1
            )
            : [];

    return (
        <div
            style={{
                width: "100%",
                height: 450,
            }}
        >
            <ResponsiveContainer>
                <BarChart
                    data={data}
                    margin={{
                        top: 20,
                        right: 20,
                        bottom: 40,
                        left: 30,
                    }}
                >
                    <CartesianGrid
                        strokeDasharray="3 3"
                    />

                    <XAxis
                        dataKey="bucket"
                        angle={-30}
                        textAnchor="end"
                        interval={0}
                    />

                    <YAxis
                        tickFormatter={(value) =>
                            `${value}%`
                        }
                    />

                    <Tooltip
                        formatter={(
                            value,
                            name,
                            props
                        ) => {
                            if (
                                name ===
                                "Paid spin cycles"
                            ) {
                                return [
                                    `${Number(value).toFixed(
                                        2
                                    )}% (${props.payload.cycles.toLocaleString()} cycles)`,
                                    "Paid spin cycles",
                                ];
                            }

                            return value;
                        }}
                    />

                    <Bar
                        dataKey="percentage"
                        name="Paid spin cycles"
                        fill="#4f7cff"
                    />
                </BarChart>
            </ResponsiveContainer>
        </div>

    );
}